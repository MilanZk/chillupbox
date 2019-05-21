package com.company.mobile.android.appname.app.common

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

/**
 * Base [Fragment] class for every fragment in this application.
 */
open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // At this point, Kotlin extensions are available
        earlyInitializeViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeState(savedInstanceState)
        initializeViews(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    /**
     * View initialization that does not depend on view models, like setting up a recycler view.
     *
     * Called before [initializeState].
     */
    @CallSuper
    protected open fun earlyInitializeViews() {
    }

    /**
     * Initializes fragment state with [androidx.lifecycle.ViewModel]s and parameters passed through [Bundle].
     *
     * Called before [initializeViews] and after [earlyInitializeViews].
     */
    @CallSuper
    protected open fun initializeState(savedInstanceState: Bundle?) {
    }

    /**
     * View initialization that depends on view models.
     *
     * Called before [initializeContents] and after [initializeState].
     */
    @CallSuper
    protected open fun initializeViews(savedInstanceState: Bundle?) {
    }

    /**
     * Initializes view contents.
     *
     * Called after [initializeViews].
     */
    @CallSuper
    protected open fun initializeContents(savedInstanceState: Bundle?) {
    }
}
