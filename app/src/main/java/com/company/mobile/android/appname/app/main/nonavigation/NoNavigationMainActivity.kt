package com.company.mobile.android.appname.app.main.nonavigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosFragment

class NoNavigationMainActivity : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, NoNavigationMainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_navigation_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_no_navigation_main_container, BufferoosFragment.newInstance())
                .commitNow()
        }
    }
}