package com.company.mobile.android.appname.app.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.company.mobile.android.appname.app.BuildConfig
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosFragment
import com.company.mobile.android.appname.app.common.BaseActivity
import com.company.mobile.android.appname.app.search.SearchFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.dl_main_drawer_layout
import kotlinx.android.synthetic.main.activity_main.nv_main_drawer_navigation_view
import kotlinx.android.synthetic.main.activity_main.tv_main_drawer_footer_text
import kotlinx.android.synthetic.main.main_app_bar.tb_main_toolbar
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val SCOPE_NAME = (this::class.java.canonicalName ?: "MainActivity") + hashCode()
    private val VERSION_NAME = BuildConfig.VERSION_NAME
    private lateinit var currentTagFlag: String
    private var currentMenuItemId: Int = 0

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
            // IMPORTANT: Back button does not navigate between Navigation Drawer Views.
            // See: https://material.io/design/components/bottom-navigation.html#behavior
            if (exitSnackBar.isShown) {
                finish()
            } else {
                exitSnackBar.show()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Remove checked state from current selected item
        nv_main_drawer_navigation_view.menu.findItem(currentMenuItemId)?.let {
            it.isChecked = false
        }
        // Set checked state for new selected item
        currentMenuItemId = item.itemId
        item.isChecked = true

        // Handle navigation view item clicks here
        when (item.itemId) {
            R.id.main_drawer_menu_maintenance_visits -> {
                clearBackStackInclusive(currentTagFlag)
                currentTagFlag = BufferoosFragment.TAG
                pushFragment(R.id.fl_main_content, BufferoosFragment.newInstance(), currentTagFlag)
                setTitle(getString(R.string.drawer_menu_bufferoos))
            }
            R.id.main_drawer_menu_search -> {
                clearBackStackInclusive(currentTagFlag)
                currentTagFlag = SearchFragment.TAG
                pushFragment(R.id.fl_main_content, SearchFragment.newInstance(), currentTagFlag)
                setTitle(getString(R.string.drawer_menu_search))
            }
            R.id.main_drawer_menu_sign_out -> {
                finish()
            }
        }

        dl_main_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initializeContents(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            currentTagFlag = BufferoosFragment.TAG
            val menuItem = nv_main_drawer_navigation_view.menu.getItem(0)
            currentMenuItemId = menuItem.itemId
            menuItem.isChecked = true
            pushFragment(R.id.fl_main_content, BufferoosFragment.newInstance(), currentTagFlag)
            setTitle(getString(R.string.drawer_menu_bufferoos))
        }
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

    fun setTitle(@NonNull title: String) {
        // Set title in activity bar
        supportActionBar?.title = title
    }
}