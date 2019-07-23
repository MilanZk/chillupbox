package com.zgz.mobile.android.prepexam.app.main.nonavigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import com.zgz.mobile.android.prepexam.app.R
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_no_navigation_main.fl_no_navigation_main_container
import kotlinx.android.synthetic.main.activity_no_navigation_main.tb_no_navigation_main_toolbar
import kotlinx.android.synthetic.main.navigation_drawer_main_content.fl_navigation_drawer_main_content
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NoNavigationMainActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, NoNavigationMainActivity::class.java)
        }
    }

    private val noNavigationMainActivityViewModel: NoNavigationMainActivityViewModel by viewModel()
    private val bufferoosViewModel: BufferoosViewModel by viewModel()
    private lateinit var exitSnackBar: Snackbar
    private val commonLiveEvent = SingleLiveEvent<CommonEvent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_no_navigation_main)

        initializeViews(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.no_navigation_tool_bar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.no_navigation_menu_sign_out -> {
                noNavigationMainActivityViewModel.signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
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

    private fun initializeViews(savedInstanceState: Bundle?) {
        // Initialize toolbar
        setSupportActionBar(tb_no_navigation_main_toolbar)

        if (savedInstanceState == null) {
            pushSectionFragment(BufferoosFragment.newInstance(), BufferoosFragment.TAG)
        }

        // Update the icon according to the back stack size, this is a must when recreating the activity (rotation)
        updateToolBarHomeIcon(supportFragmentManager.backStackEntryCount > 1)

        // Initialize exit snack bar
        exitSnackBar = Snackbar.make(fl_no_navigation_main_container, R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    private fun initializeContents(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
        bufferoosViewModel.bufferoosNavigationLiveEvent.observe(this, Observer { command ->
            when (command) {
                GoToDetailsView -> {
                    pushSectionFragment(BufferooDetailsFragment.newInstance(), BufferooDetailsFragment.TAG)
                }
            }
            // After pushing a fragment, show back button must be true
            updateToolBarHomeIcon(true)
        })

        noNavigationMainActivityViewModel.getSignOut().observe(this,
            Observer<NoNavigationSignOutState> {
                if (it != null) handleDataState(it)
            }
        )

        // Observe events communicated through the common event channel shared by all the view models that inherit from CommonEventsViewModel
        commonLiveEvent.observe(this, Observer {
            when (it) {
                Unauthorized -> {
                    Toast.makeText(this, R.string.sign_out_unauthorized, Toast.LENGTH_LONG).show()
                    noNavigationMainActivityViewModel.signOut()
                }
            }
        })

        // Set commonLiveEvent channel in every view model used by this activity that inherits from CommonEventsViewModel
        bufferoosViewModel.commonLiveEvent = commonLiveEvent
    }

    private fun updateToolBarHomeIcon(showBackButton: Boolean) {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.let { actionBar ->
            if (showBackButton) {
                // Show back button
                actionBar.setDisplayHomeAsUpEnabled(true)
                //actionBar.setHomeAsUpIndicator(0) // According to documentation, a resource id of 0 sets the default theme icon for back button
            } else {
                // Hide back button
                actionBar.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    private fun pushSectionFragment(sectionFragment: BaseFragment, sectionTag: String) {
        // Push a fragment for current section that will be added to the back stack
        pushFragment(R.id.fl_no_navigation_main_container, sectionFragment, sectionTag)
    }

    private fun handleDataState(noNavigationSignOutState: NoNavigationSignOutState) {
        // This is half baked, it should be improved
        when (noNavigationSignOutState) {
            is Loading -> Timber.w("Loading not implemented")
            is Success -> {
                Navigator.navigateToSignInActivity(this)
                finish()
            }
            is Error -> Snackbar.make(fl_navigation_drawer_main_content, noNavigationSignOutState.errorBundle.stringId, Snackbar.LENGTH_SHORT).show()
        }
    }
}