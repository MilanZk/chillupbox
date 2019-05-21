package com.company.mobile.android.appname.app.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.company.mobile.android.appname.app.BuildConfig
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.detail.BufferooDetailsFragment
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosFragment
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosViewModel
import com.company.mobile.android.appname.app.common.BaseActivity
import com.company.mobile.android.appname.app.common.BaseFragment
import com.company.mobile.android.appname.app.about.AboutFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.dl_main_drawer_layout
import kotlinx.android.synthetic.main.activity_main.nv_main_drawer_navigation_view
import kotlinx.android.synthetic.main.activity_main.tv_main_drawer_footer_text
import kotlinx.android.synthetic.main.main_app_bar.tb_main_toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val VERSION_NAME = BuildConfig.VERSION_NAME

    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private val bufferoosViewModel: BufferoosViewModel by viewModel()
    private lateinit var exitSnackBar: Snackbar
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private var toolBarNavigationListenerIsRegistered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews(savedInstanceState)
        initializeState(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (drawerToggle.isDrawerIndicatorEnabled) {
                    dl_main_drawer_layout.openDrawer(GravityCompat.START)
                } else {
                    onBackPressed()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (dl_main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            dl_main_drawer_layout.closeDrawer(GravityCompat.START)
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
                dl_main_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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
                dl_main_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

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
        if (item.itemId != mainActivityViewModel.currentMenuItemId) {
            // Remove checked state from current selected item
            nv_main_drawer_navigation_view.menu.findItem(mainActivityViewModel.currentMenuItemId)?.let {
                it.isChecked = false
            }
            // Set checked state for new selected item
            mainActivityViewModel.currentMenuItemId = item.itemId
            item.isChecked = true

            // Handle navigation view item clicks here
            when (item.itemId) {
                R.id.main_drawer_menu_bufferoos -> {
                    changeSection(BufferoosFragment.TAG, BufferoosFragment.newInstance(), R.string.drawer_menu_bufferoos)
                }
                R.id.main_drawer_menu_about -> {
                    changeSection(AboutFragment.TAG, AboutFragment.newInstance(), R.string.drawer_menu_about)
                }
                R.id.main_drawer_menu_sign_out -> {
                    finish()
                }
            }
        }

        dl_main_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initializeContents(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            // First time initialization
            mainActivityViewModel.currentSectionFragmentTag = BufferoosFragment.TAG
            val menuItem = nv_main_drawer_navigation_view.menu.getItem(0)
            mainActivityViewModel.currentMenuItemId = menuItem.itemId
            menuItem.isChecked = true
            pushSectionFragment(BufferoosFragment.newInstance(), mainActivityViewModel.currentSectionFragmentTag, R.string.drawer_menu_bufferoos)
        } else {
            // After recreation (rotation)
            nv_main_drawer_navigation_view.menu.findItem(mainActivityViewModel.currentMenuItemId)?.let {
                it.isChecked = true
            }
        }

        bufferoosViewModel.bufferoosNavigationEvent.observe(this, Observer { command ->
            when (command) {
                GoToDetailsView -> {
                    pushSectionFragment(BufferooDetailsFragment.newInstance(), BufferooDetailsFragment.TAG, R.string.bufferoo_details_title)
                }
            }
            // After pushing a fragment, show back button must be true
            updateToolBarHomeIcon(true)
        })
    }

    private fun initializeState(savedInstanceState: Bundle?) {
    }

    private fun initializeViews(savedInstanceState: Bundle?) {
        // Initialize toolbar
        setSupportActionBar(tb_main_toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        // Initialize navigation drawer
        nv_main_drawer_navigation_view.setNavigationItemSelectedListener(this)
        // Initialize ActionBarDrawerToggle, which will control toggle of hamburger
        drawerToggle = ActionBarDrawerToggle(this, dl_main_drawer_layout, tb_main_toolbar, R.string.drawer_menu_open, R.string.drawer_menu_close)
        // Setting the actionbarToggle to drawer layout
        dl_main_drawer_layout.addDrawerListener(drawerToggle)
        // Calling sync state is necessary to show your hamburger icon
        drawerToggle.syncState()

        // Update the icon according to the back stack size, this is a must when recreating the activity (rotation)
        updateToolBarHomeIcon(supportFragmentManager.backStackEntryCount > 1)

        tv_main_drawer_footer_text.text = resources.getString(R.string.drawer_menu_footer_text, VERSION_NAME)

        // Initialize exit snack bar
        exitSnackBar = Snackbar.make(nv_main_drawer_navigation_view, R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    private fun changeSection(sectionTag: String, sectionFragment: BaseFragment, @StringRes sectionTitleStringId: Int) {
        // Clear completely the back stack (not a single transition should remain)
        clearBackStackInclusive(mainActivityViewModel.currentSectionFragmentTag)
        // Store current fragment tag to completely clear the back stack when another menu
        // item is selected in the future.
        mainActivityViewModel.currentSectionFragmentTag = sectionTag
        // Push initial fragment for this section. Any new fragment pushed by this section
        // will be added to the back stack, and all of them will be removed when changing
        // section with clearBackStackInclusive() function.
        pushSectionFragment(sectionFragment, mainActivityViewModel.currentSectionFragmentTag, sectionTitleStringId)
    }

    private fun pushSectionFragment(sectionFragment: BaseFragment, sectionTag: String, @StringRes sectionTitleStringId: Int) {
        // Push a fragment for current section that will be added to the back stack
        pushFragment(R.id.fl_main_content, sectionFragment, sectionTag)
        // Update toolbar title
        setTitle(getString(sectionTitleStringId))
    }
}