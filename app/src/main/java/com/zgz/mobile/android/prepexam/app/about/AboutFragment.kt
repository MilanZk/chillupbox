package com.zgz.mobile.android.prepexam.app.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zgz.mobile.android.prepexam.app.R
import com.zgz.mobile.android.prepexam.app.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.ev_about_empty_view
import kotlinx.android.synthetic.main.fragment_about.ev_about_error_view
import kotlinx.android.synthetic.main.fragment_about.lv_about_loading_view

class AboutFragment : BaseFragment() {

    companion object {
        val TAG = AboutFragment::class.java.canonicalName
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.setTitle(R.string.bufferoo_about_title)
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        ev_about_error_view.visibility = View.GONE
        ev_about_empty_view.visibility = View.GONE
        lv_about_loading_view.visibility = View.GONE
    }
}
