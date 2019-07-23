package com.zgz.mobile.android.prepexam.app.common.navigation

import android.app.Activity
import androidx.annotation.NonNull
import com.zgz.mobile.android.prepexam.app.account.SignInActivity
import com.zgz.mobile.android.prepexam.app.choosenavigation.ChooseNavigationActivity
import com.zgz.mobile.android.prepexam.app.main.navigationdrawer.NavigationDrawerMainActivity
import com.zgz.mobile.android.prepexam.app.main.nonavigation.NoNavigationMainActivity

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
     * Opens choose navigation example screen.
     *
     * @param activity An [Activity] needed to open the destiny activity.
     */
    fun navigateToChooseNavigationActivity(@NonNull activity: Activity) {
        val intentToLaunch = ChooseNavigationActivity.getCallingIntent(activity)
        activity.startActivity(intentToLaunch)
    }

    /**
     * Opens navigation drawer main screen.
     *
     * @param activity An [Activity] needed to open the destiny activity.
     */
    fun navigateToNavigationDrawerMainActivity(@NonNull activity: Activity) {
        val intentToLaunch = NavigationDrawerMainActivity.getCallingIntent(activity)
        // Forces a complete reopen of main activity
        // intentToLaunch.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intentToLaunch)
    }

    /**
     * Opens no navigation main screen.
     *
     * @param activity An [Activity] needed to open the destiny activity.
     */
    fun navigateToNoNavigationActivity(@NonNull activity: Activity) {
        val intentToLaunch = NoNavigationMainActivity.getCallingIntent(activity)
        activity.startActivity(intentToLaunch)
    }
}
