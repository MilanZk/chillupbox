package com.hopovo.mobile.android.prepexam.app.bufferoos.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.BaseFragment
import com.hopovo.mobile.android.prepexam.app.exercise.master.ExerciseViewModel
import kotlinx.android.synthetic.main.fragment_bufferoo_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BufferooDetailsFragment : BaseFragment() {

    companion object {
        val TAG = BufferooDetailsFragment::class.java.canonicalName
        fun newInstance() = BufferooDetailsFragment()
    }

    private val mExerciseViewModel: ExerciseViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_bufferoo_details, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.setTitle(R.string.bufferoo_details_title)
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        ev_bufferoo_details_error_view.visibility = View.GONE
        ev_bufferoo_details_empty_view.visibility = View.GONE
        lv_bufferoo_details_loading_view.visibility = View.GONE

        // Link the fragment and the model view with "viewLifecycleOwner", so that observers
        // can be subscribed in onActivityCreated() and can be automatically unsubscribed
        // in onDestroyView().
        // IMPORTANT: Never use "this" as lifecycle owner.
        // See: https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808

    }


}
