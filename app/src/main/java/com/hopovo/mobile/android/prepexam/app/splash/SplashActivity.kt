package com.hopovo.mobile.android.prepexam.app.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.hopovo.mobile.android.prepexam.app.common.BaseActivity
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorDialogFragment.ErrorDialogFragmentListener
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.*
import com.hopovo.mobile.android.prepexam.app.common.navigation.Navigator
import kotlinx.android.synthetic.main.navigation_drawer_main_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SplashActivity : BaseActivity(), ErrorDialogFragmentListener {

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
                } else {
                    Navigator.navigateToSignInActivity(this)
                }
                finish()
            }
            is Error -> Snackbar.make(fl_navigation_drawer_main_content, credentialsState.errorBundle.stringId, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onErrorDialogAccepted(action: Long, retry: Boolean) {
        // TODO: Implement
        Timber.d("User accepted error dialog")
    }

    override fun onErrorDialogCancelled(action: Long) {
        // TODO: Implement
        Timber.d("User cancelled error dialog")
    }
}