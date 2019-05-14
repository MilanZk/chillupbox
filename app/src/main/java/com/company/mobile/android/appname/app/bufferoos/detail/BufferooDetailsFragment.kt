package com.company.mobile.android.appname.app.bufferoos.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_bufferoo_details.ev_bufferoo_details_empty_view
import kotlinx.android.synthetic.main.fragment_bufferoo_details.ev_bufferoo_details_error_view
import kotlinx.android.synthetic.main.fragment_bufferoo_details.pb_bufferoo_details_progress

class BufferooDetailsFragment : BaseFragment() {

    companion object {
        val TAG = BufferooDetailsFragment::class.java.canonicalName
        fun newInstance() = BufferooDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_bufferoo_details, container, false)
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
        ev_bufferoo_details_error_view.visibility = View.GONE
        ev_bufferoo_details_empty_view.visibility = View.GONE
        pb_bufferoo_details_progress.visibility = View.GONE
    }
}
