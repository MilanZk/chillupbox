package com.company.mobile.android.appname.app.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.company.mobile.android.appname.app.common.BaseActivity
import com.company.mobile.android.appname.app.common.navigation.Navigator

class SplashActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Navigator.navigateToMainActivity(this)
        finish()
    }
}