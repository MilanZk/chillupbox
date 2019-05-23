package com.company.mobile.android.appname.app.main.nonavigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.detail.BufferooDetailsFragment
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosFragment
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosViewModel
import com.company.mobile.android.appname.app.common.BaseActivity
import com.company.mobile.android.appname.app.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_no_navigation_main.fl_no_navigation_main_container
import kotlinx.android.synthetic.main.activity_no_navigation_main.tb_no_navigation_main_toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoNavigationMainActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, NoNavigationMainActivity::class.java)
        }
    }

    private val bufferoosViewModel: BufferoosViewModel by viewModel()
    private lateinit var exitSnackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_no_navigation_main)

        initializeViews(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
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

    private fun initializeContents(savedInstanceState: Bundle?) {
        bufferoosViewModel.bufferoosNavigationEvent.observe(this, Observer { command ->
            when (command) {
                GoToDetailsView -> {
                    pushSectionFragment(BufferooDetailsFragment.newInstance(), BufferooDetailsFragment.TAG)
                }
            }
            // After pushing a fragment, show back button must be true
            updateToolBarHomeIcon(true)
        })
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
}