package com.company.mobile.android.appname.app.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseActivity

class SignInActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_basic_main_container, SignInFragment.newInstance())
                .commitNow()
        }
    }
}