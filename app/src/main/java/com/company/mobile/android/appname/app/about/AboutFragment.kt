package com.company.mobile.android.appname.app.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.ev_about_empty_view
import kotlinx.android.synthetic.main.fragment_about.ev_about_error_view
import kotlinx.android.synthetic.main.fragment_about.pb_about_progress

class AboutFragment : BaseFragment() {

    companion object {
        val TAG = AboutFragment::class.java.canonicalName
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // At this point, Kotlin extensions are available
        earlyInitializeViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeState(savedInstanceState)
        initializeViews()
        initializeContents()
    }

    override fun onResume() {
        super.onResume()

        activity?.setTitle(R.string.bufferoo_about_title)
    }

    /**
     * View initialization that does not depend on view models.
     */
    private fun earlyInitializeViews() {
    }

    /**
     * Initializes fragment state with [androidx.lifecycle.ViewModel]s and parameters passed through [Bundle].
     */
    private fun initializeState(savedInstanceState: Bundle?) {
    }

    /**
     * View initialization that depends on view models.
     */
    private fun initializeViews() {
    }

    /**
     * Initializes view contents.
     */
    private fun initializeContents() {
        ev_about_error_view.visibility = View.GONE
        ev_about_empty_view.visibility = View.GONE
        pb_about_progress.visibility = View.GONE
    }
}
