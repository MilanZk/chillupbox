package com.company.mobile.android.appname.app.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.dl_main_drawer_layout
import kotlinx.android.synthetic.main.activity_main.nv_main_drawer_navigation_view
import kotlinx.android.synthetic.main.main_app_bar.tb_main_toolbar
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val SCOPE_NAME = (this::class.java.canonicalName ?: "MainActivity") + hashCode()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindScope(getOrCreateScope(SCOPE_NAME))

        // Initialize toolbar
        setupActionBar()

        // Initialize navigation drawer
        nv_main_drawer_navigation_view.setNavigationItemSelectedListener(this)
        setupDrawerToggle()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_main_content, BufferoosFragment.newInstance())
                .commitNow()
        }
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
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.main_drawer_menu_maintenance_visits -> {

            }
            R.id.main_drawer_menu_search -> {

            }
            R.id.main_drawer_menu_sign_out -> {

            }
        }

        dl_main_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupActionBar() {
        setSupportActionBar(tb_main_toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    private fun setupDrawerToggle() {
        val toggle = ActionBarDrawerToggle(this, dl_main_drawer_layout, tb_main_toolbar, R.string.drawer_menu_open, R.string.drawer_menu_close)
        dl_main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }
}