package com.company.mobile.android.appname.app.common.navigation

import android.app.Activity
import androidx.annotation.NonNull
import com.company.mobile.android.appname.app.choosenavigation.ChooseNavigationActivity
import com.company.mobile.android.appname.app.main.navigationdrawer.NavigationDrawerMainActivity

/**
 * Class used to navigate through activities.
 */
object Navigator {

    /**
     * Opens navigation drawer main screen.
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
}
