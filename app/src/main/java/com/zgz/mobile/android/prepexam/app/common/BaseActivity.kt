package com.zgz.mobile.android.prepexam.app.common

import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

/**
 * Base [AppCompatActivity] class for every Activity in this application.
 */
abstract class BaseActivity : AppCompatActivity() {

//    protected fun popFragment() {
//        val fragmentManager = supportFragmentManager
//        if (fragmentManager.backStackEntryCount > 1) {
//            // Pop fragments while more than one remains in the stack
//            fragmentManager.popBackStack()
//        } else {
//            // If only one fragment remains in the stack, finish the activity to avoid leaving the fragment container
//            // without fragments, thus showing an empty screen.
//            this.finish()
//        }
//    }

    fun clearBackStackInclusive(@NonNull tag: String) {
        supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun pushFragment(@IdRes idResourceReference: Int, fragment: BaseFragment, tag: String) {
        val fragmentManager = supportFragmentManager

        // Create new transaction
        val transaction = fragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment. Replace operation has two side effects
        // over add operation. First, the onResume() and onPause() methods will be always called in the fragment, this
        // will not happen with add, making much more difficult to deal with state transitions and working with the
        // presenter. Second, there is always only one fragment in the activity; more than one fragment is placed in the
        // container with add operation, thus increasing resource consumption and stacking views (if the top fragment
        // has a transparent area, the fragment below can be seen).
        transaction.replace(idResourceReference, fragment)

        // Add the transaction to the back stack to allow navigation with back button
        transaction.addToBackStack(tag)

        // Commit the transaction
        transaction.commit()
    }
}
