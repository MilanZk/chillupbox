package com.company.mobile.android.appname.app.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosFragment
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope

class MainActivity : AppCompatActivity() {

    private val SCOPE_NAME = (this::class.java.canonicalName ?: "MainActivity") + hashCode()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindScope(getOrCreateScope(SCOPE_NAME))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, BufferoosFragment.newInstance())
                .commitNow()
        }
    }
}