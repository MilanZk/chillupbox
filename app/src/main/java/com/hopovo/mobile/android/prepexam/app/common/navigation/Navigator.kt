package com.hopovo.mobile.android.prepexam.app.common.navigation

import android.app.Activity
import androidx.annotation.NonNull
import com.hopovo.mobile.android.prepexam.app.account.SignInActivity
import com.hopovo.mobile.android.prepexam.app.main.MainActivity

/**
 * Class used to navigate through activities.
 */
object Navigator {

    /**
     * Opens sign in screen.
     *
     * @param activity An [Activity] needed to open the destiny activity.
     */
    fun navigateToSignInActivity(@NonNull activity: Activity) {
        val intentToLaunch = SignInActivity.getCallingIntent(activity)
        activity.startActivity(intentToLaunch)
    }

    /**
     * Opens navigation drawer main screen.
     *
     * @param activity An [Activity] needed to open the destiny activity.
     */
    fun navigateToMainActivity(@NonNull activity: Activity) {
        val intentToLaunch = MainActivity.getCallingIntent(activity)
        activity.startActivity(intentToLaunch)
    }
}
