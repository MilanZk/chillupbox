package com.company.mobile.android.appname.app.common.navigation

import android.app.Activity
import androidx.annotation.NonNull
import com.company.mobile.android.appname.app.main.MainActivity

/**
 * Class used to navigate through activities.
 */
object Navigator {

    /**
     * Opens main screen.
     *
     * @param activity An [Activity] needed to open the destiny activity.
     */
    fun navigateToMainActivity(@NonNull activity: Activity) {
        val intentToLaunch = MainActivity.getCallingIntent(activity)
        // Forces a complete reopen of main activity
        // intentToLaunch.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intentToLaunch)
    }
}
