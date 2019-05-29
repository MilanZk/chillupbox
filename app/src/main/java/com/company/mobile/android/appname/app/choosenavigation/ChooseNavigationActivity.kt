package com.company.mobile.android.appname.app.choosenavigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseActivity

class ChooseNavigationActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, ChooseNavigationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_basic_main_container, ChooseNavigationFragment.newInstance())
                .commitNow()
        }
    }
}