package com.zgz.mobile.android.prepexam.app.main.navigationdrawer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.zgz.mobile.android.prepexam.app.BuildConfig
import com.zgz.mobile.android.prepexam.app.R
import com.zgz.mobile.android.prepexam.app.about.AboutFragment
import com.zgz.mobile.android.prepexam.app.bufferoos.detail.BufferooDetailsFragment
import com.zgz.mobile.android.prepexam.app.bufferoos.master.BufferoosFragment
import com.zgz.mobile.android.prepexam.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import com.zgz.mobile.android.prepexam.app.bufferoos.viewmodel.BufferoosViewModel
import com.zgz.mobile.android.prepexam.app.common.BaseActivity
import com.zgz.mobile.android.prepexam.app.common.BaseFragment
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Error
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Loading
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Success
import com.zgz.mobile.android.prepexam.app.common.navigation.Navigator
import com.zgz.mobile.android.prepexam.app.common.viewmodel.CommonEvent
import com.zgz.mobile.android.prepexam.app.common.viewmodel.CommonEvent.Unauthorized
import com.zgz.mobile.android.prepexam.app.common.viewmodel.SingleLiveEvent
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_navigation_drawer_main.dl_navidation_drawer_drawer_layout
import kotlinx.android.synthetic.main.activity_navigation_drawer_main.nv_navigation_drawer_navigation_view
import kotlinx.android.synthetic.main.activity_navigation_drawer_main.tv_navigation_drawer_footer_text
import kotlinx.android.synthetic.main.navigation_drawer_main_app_bar.tb_navigation_drawer_main_toolbar
import kotlinx.android.synthetic.main.navigation_drawer_main_content.fl_navigation_drawer_main_content
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NavigationDrawerMainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val VERSION_NAME = BuildConfig.VERSION_NAME

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, NavigationDrawerMainActivity::class.java)
        }
    }

    private val navigationDrawerMainActivityViewModel: NavigationDrawerMainActivityViewModel by viewModel()
    private val bufferoosViewModel: BufferoosViewModel by viewModel()
    private lateinit var exitSnackBar: Snackbar
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private var toolBarNavigationListenerIsRegistered = false
    private val commonLiveEvent = SingleLiveEvent<CommonEvent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer_main)

        initializeViews(savedInstanceState)
        initializeState(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (drawerToggle.isDrawerIndicatorEnabled) {
                    dl_navidation_drawer_drawer_layout.openDrawer(GravityCompat.START)
                } else {
                    onBackPressed()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (dl_navidation_drawer_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            dl_navidation_drawer_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount > 1) {
                // Update home button here before popping back stack, because back stack pop operation is asynchronous
                // and it is not possible to accurately know (race condition) the actual number of fragments just after
                // calling popBackStack().
                // If back stack size is 2, show back button is false because a fragment will be popped.
                // If back stack size is greater than 2, show back button must be true.
                val showBackButton = supportFragmentManager.backStackEntryCount > 2
                // Pop fragments while more than one remains in the stack
                supportFragmentManager.popBackStack()
                // Update home icon now, because if it is updated before popBackStack(), the hamburger icon is shown
                // before popping the fragment.
                updateToolBarHomeIcon(showBackButton)
            } else {
                // IMPORTANT: Back button does not navigate between Navigation Drawer Views.
                // See: https://material.io/design/components/bottom-navigation.html#behavior
                if (exitSnackBar.isShown) {
                    finish()
                } else {
                    exitSnackBar.show()
                }
            }
        }
    }

    private fun updateToolBarHomeIcon(showBackButton: Boolean) {
        // See: https://stackoverflow.com/a/36677279/5189200
        // To keep states of ActionBar and ActionBarDrawerToggle synchronized, when one is enabled, the other must be
        // disabled.
        // And as you may notice, the order for this operation is first disable, then enable - VERY VERY IMPORTANT.
        val actionbar: ActionBar? = supportActionBar
        actionbar?.let { actionBar ->
            if (showBackButton) {
                // You may not want to open the drawer on swipe from the left in this case
                dl_navidation_drawer_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                // Remove hamburger
                drawerToggle.isDrawerIndicatorEnabled = false
                // Show back button
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(0) // According to documentation, a resource id of 0 sets the default theme icon for back button
                // When DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
                // clicks are disabled i.e. the UP button will not work.
                // We need to add a listener, as in below, so DrawerToggle will forward
                // click events to this listener.
                if (!toolBarNavigationListenerIsRegistered) {
                    drawerToggle.toolbarNavigationClickListener = View.OnClickListener {
                        // Doesn't have to be onBackPressed
                        onBackPressed()
                    }

                    toolBarNavigationListenerIsRegistered = true
                }
            } else {
                // You must regain the power of swipe for the drawer.
                dl_navidation_drawer_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

                // Remove back button
                actionBar.setDisplayHomeAsUpEnabled(true)
                //actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu) // Do NOT uncomment or this solution will not work!!!
                // Show hamburger
                drawerToggle.isDrawerIndicatorEnabled = true
                // Remove the/any drawer toggle listener
                drawerToggle.toolbarNavigationClickListener = null
                toolBarNavigationListenerIsRegistered = false
            }

            // So, one may think "Hmm why not simplify to:
            // .....
            // supportActionBar!!.setDisplayHomeAsUpEnabled(showBackButton);
            // drawerToggle.isDrawerIndicatorEnabled = !showBackButton;
            // ......
            // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId != navigationDrawerMainActivityViewModel.currentMenuItemId) {
            // Remove checked state from current selected item
            nv_navigation_drawer_navigation_view.menu.findItem(navigationDrawerMainActivityViewModel.currentMenuItemId)?.let {
                it.isChecked = false
            }
            // Set checked state for new selected item
            navigationDrawerMainActivityViewModel.currentMenuItemId = item.itemId
            item.isChecked = true

            // Handle navigation view item clicks here
            when (item.itemId) {
                R.id.main_drawer_menu_bufferoos -> {
                    changeSection(BufferoosFragment.TAG, BufferoosFragment.newInstance())
                }
                R.id.main_drawer_menu_about -> {
                    changeSection(AboutFragment.TAG, AboutFragment.newInstance())
                }
                R.id.main_drawer_menu_sign_out -> {
                    navigationDrawerMainActivityViewModel.signOut()
                }
            }
        }

        dl_navidation_drawer_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initializeContents(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            // First time initialization
            navigationDrawerMainActivityViewModel.currentSectionFragmentTag = BufferoosFragment.TAG
            val menuItem = nv_navigation_drawer_navigation_view.menu.getItem(0)
            navigationDrawerMainActivityViewModel.currentMenuItemId = menuItem.itemId
            menuItem.isChecked = true
            pushSectionFragment(BufferoosFragment.newInstance(), navigationDrawerMainActivityViewModel.currentSectionFragmentTag)
        } else {
            // After recreation (rotation)
            nv_navigation_drawer_navigation_view.menu.findItem(navigationDrawerMainActivityViewModel.currentMenuItemId)?.let {
                it.isChecked = true
            }
        }

        bufferoosViewModel.bufferoosNavigationLiveEvent.observe(this, Observer { command ->
            when (command) {
                GoToDetailsView -> {
                    pushSectionFragment(BufferooDetailsFragment.newInstance(), BufferooDetailsFragment.TAG)
                }
            }
            // After pushing a fragment, show back button must be true
            updateToolBarHomeIcon(true)
        })

        navigationDrawerMainActivityViewModel.getSignOut().observe(this,
            Observer<NavigationDrawerSignOutState> {
                if (it != null) handleDataState(it)
            }
        )

        // Observe events communicated through the common navigation event channel shared by all the view models that inherit from CommonEventsViewModel
        commonLiveEvent.observe(this, Observer {
            when (it) {
                Unauthorized -> {
                    Toast.makeText(this, R.string.sign_out_unauthorized, Toast.LENGTH_LONG).show()
                    navigationDrawerMainActivityViewModel.signOut()
                }
            }
        })

        // Set commonLiveEvent channel in every view model used by this activity that inherits from CommonEventsViewModel
        bufferoosViewModel.commonLiveEvent = commonLiveEvent
    }

    private fun initializeState(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
    }

    private fun initializeViews(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
        // Initialize toolbar
        setSupportActionBar(tb_navigation_drawer_main_toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        // Initialize navigation drawer
        nv_navigation_drawer_navigation_view.setNavigationItemSelectedListener(this)
        // Initialize ActionBarDrawerToggle, which will control toggle of hamburger
        drawerToggle = ActionBarDrawerToggle(this, dl_navidation_drawer_drawer_layout, tb_navigation_drawer_main_toolbar, R.string.navigation_drawer_menu_open, R.string.navigation_drawer_menu_close)
        // Setting the actionbarToggle to drawer layout
        dl_navidation_drawer_drawer_layout.addDrawerListener(drawerToggle)
        // Calling sync state is necessary to show your hamburger icon
        drawerToggle.syncState()

        // Update the icon according to the back stack size, this is a must when recreating the activity (rotation)
        updateToolBarHomeIcon(supportFragmentManager.backStackEntryCount > 1)

        tv_navigation_drawer_footer_text.text = resources.getString(R.string.navigation_drawer_menu_footer_text, VERSION_NAME)

        // Initialize exit snack bar
        exitSnackBar = Snackbar.make(nv_navigation_drawer_navigation_view, R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    private fun changeSection(sectionTag: String, sectionFragment: BaseFragment) {
        // Clear completely the back stack (not a single transition should remain)
        clearBackStackInclusive(navigationDrawerMainActivityViewModel.currentSectionFragmentTag)
        // Store current fragment tag to completely clear the back stack when another menu
        // item is selected in the future.
        navigationDrawerMainActivityViewModel.currentSectionFragmentTag = sectionTag
        // Push initial fragment for this section. Any new fragment pushed by this section
        // will be added to the back stack, and all of them will be removed when changing
        // section with clearBackStackInclusive() function.
        pushSectionFragment(sectionFragment, navigationDrawerMainActivityViewModel.currentSectionFragmentTag)
    }

    private fun pushSectionFragment(sectionFragment: BaseFragment, sectionTag: String) {
        // Push a fragment for current section that will be added to the back stack
        pushFragment(R.id.fl_navigation_drawer_main_content, sectionFragment, sectionTag)
    }

    private fun handleDataState(navigationDrawerSignOutState: NavigationDrawerSignOutState) {
        // This is half baked, it should be improved
        when (navigationDrawerSignOutState) {
            is Loading -> Timber.w("Loading not implemented")
            is Success -> {
                Navigator.navigateToSignInActivity(this)
                finish()
            }
            is Error -> Snackbar.make(fl_navigation_drawer_main_content, navigationDrawerSignOutState.errorBundle.stringId, Snackbar.LENGTH_SHORT).show()
        }
    }
}