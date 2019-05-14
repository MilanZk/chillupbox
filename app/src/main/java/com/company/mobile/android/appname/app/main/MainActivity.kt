package com.company.mobile.android.appname.app.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.company.mobile.android.appname.app.BuildConfig
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.detail.BufferooDetailsFragment
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosFragment
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosViewModel
import com.company.mobile.android.appname.app.common.BaseActivity
import com.company.mobile.android.appname.app.common.BaseFragment
import com.company.mobile.android.appname.app.search.SearchFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.dl_main_drawer_layout
import kotlinx.android.synthetic.main.activity_main.nv_main_drawer_navigation_view
import kotlinx.android.synthetic.main.activity_main.tv_main_drawer_footer_text
import kotlinx.android.synthetic.main.main_app_bar.tb_main_toolbar
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val SCOPE_NAME = (this::class.java.canonicalName ?: "MainActivity") + hashCode()
    private val VERSION_NAME = BuildConfig.VERSION_NAME

    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private val bufferoosViewModel: BufferoosViewModel by viewModel()

    private lateinit var exitSnackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindScope(getOrCreateScope(SCOPE_NAME))

        initializeViews(savedInstanceState)
        initializeState(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                dl_main_drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (dl_main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            dl_main_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val fragmentManager = supportFragmentManager
            if (fragmentManager.backStackEntryCount > 1) {
                // Pop fragments while more than one remains in the stack
                fragmentManager.popBackStack()
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
                R.id.main_drawer_menu_search -> {
                    changeSection(SearchFragment.TAG, SearchFragment.newInstance(), R.string.drawer_menu_search)
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
            mainActivityViewModel.currentSectionFragmentTag = BufferoosFragment.TAG
            val menuItem = nv_main_drawer_navigation_view.menu.getItem(0)
            mainActivityViewModel.currentMenuItemId = menuItem.itemId
            menuItem.isChecked = true
            pushSectionFragment(mainActivityViewModel.currentSectionFragmentTag, BufferoosFragment.newInstance(), R.string.drawer_menu_bufferoos)
        }

        bufferoosViewModel.bufferoosNavigationEvent.observe(this, Observer { command ->
            when (command) {
                GoToDetailsView -> {
                    pushSectionFragment(BufferooDetailsFragment.TAG, BufferooDetailsFragment.newInstance(), R.string.bufferoo_details_title)
                }
            }
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
        val toggle = ActionBarDrawerToggle(this, dl_main_drawer_layout, tb_main_toolbar, R.string.drawer_menu_open, R.string.drawer_menu_close)
        dl_main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        tv_main_drawer_footer_text.text = resources.getString(R.string.drawer_menu_footer_text, VERSION_NAME)

        // Initialize exit snack bar
        exitSnackBar = Snackbar.make(nv_main_drawer_navigation_view, R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    private fun setTitle(@NonNull title: String) {
        // Set title in activity bar
        supportActionBar?.title = title
    }

    private fun changeSection(sectionTag: String, sectionFragment: BaseFragment, @StringRes sectionTitleStringId: Int) {
        // Clear completely the back stack (not a single transition should remain)
        clearBackStackInclusive(mainActivityViewModel.currentSectionFragmentTag)
        // Store current fragment tag to completely clear the back stack when another menu
        // item is selected in the future.
        mainActivityViewModel.currentSectionFragmentTag = sectionTag
        // Push initial fragment for this section. Any new fragment pushed by this section
        // will be added to the backstack, and all of them will be removed when changing
        // section with clearBackStackInclusive() function.
        pushSectionFragment(mainActivityViewModel.currentSectionFragmentTag, sectionFragment, sectionTitleStringId)
    }

    private fun pushSectionFragment(sectionTag: String, sectionFragment: BaseFragment, @StringRes sectionTitleStringId: Int) {
        // Push a fragment for current section that will be added to the backstack
        pushFragment(R.id.fl_main_content, sectionFragment, mainActivityViewModel.currentSectionFragmentTag)
        // Update toolbar title
        setTitle(getString(sectionTitleStringId))
    }
}