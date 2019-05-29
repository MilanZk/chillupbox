package com.company.mobile.android.appname.app.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.company.mobile.android.appname.app.common.BaseActivity
import com.company.mobile.android.appname.app.common.model.ResourceState.Error
import com.company.mobile.android.appname.app.common.model.ResourceState.Loading
import com.company.mobile.android.appname.app.common.model.ResourceState.Success
import com.company.mobile.android.appname.app.common.navigation.Navigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.navigation_drawer_main_content.fl_navigation_drawer_main_content
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SplashActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    private val splashActivityViewModel: SplashActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashActivityViewModel.getCredentials().observe(this,
            Observer<CredentialsState> {
                if (it != null) handleDataState(it)
            }
        )

        splashActivityViewModel.loadCredentials()
    }

    private fun handleDataState(credentialsState: CredentialsState) {
        when (credentialsState) {
            is Loading -> Timber.w("Waiting for credentials...")
            is Success -> {
                if (!TextUtils.isEmpty(credentialsState.data.username) && !TextUtils.isEmpty(credentialsState.data.id)) {
                    Navigator.navigateToChooseNavigationActivity(this)
                } else {
                    Navigator.navigateToSignInActivity(this)
                }
                finish()
            }
            is Error -> Snackbar.make(fl_navigation_drawer_main_content, credentialsState.message, Snackbar.LENGTH_SHORT).show()
        }
    }
}