package com.company.mobile.android.appname.app.bufferoos.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosViewModel
import com.company.mobile.android.appname.app.common.BaseFragment
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import kotlinx.android.synthetic.main.fragment_bufferoo_details.ev_bufferoo_details_empty_view
import kotlinx.android.synthetic.main.fragment_bufferoo_details.ev_bufferoo_details_error_view
import kotlinx.android.synthetic.main.fragment_bufferoo_details.iv_bufferoo_details_avatar_image
import kotlinx.android.synthetic.main.fragment_bufferoo_details.lv_bufferoo_details_loading_view
import kotlinx.android.synthetic.main.fragment_bufferoo_details.tv_bufferoo_details_name
import kotlinx.android.synthetic.main.fragment_bufferoo_details.tv_bufferoo_details_title
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BufferooDetailsFragment : BaseFragment() {

    companion object {
        val TAG = BufferooDetailsFragment::class.java.canonicalName
        fun newInstance() = BufferooDetailsFragment()
    }

    private val bufferoosViewModel: BufferoosViewModel by sharedViewModel()

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
        bufferoosViewModel.getSelectedBufferoo().observe(viewLifecycleOwner,
            Observer<Bufferoo> {
                this.handleData(it)
            }
        )
    }

    private fun handleData(data: Bufferoo?) {
        if (data == null) {
            // TODO: Show error
        } else {
            // Map data to UI
            tv_bufferoo_details_name.text = data.name
            tv_bufferoo_details_title.text = data.title
            context?.let {
                Glide.with(it)
                    .load(data.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(iv_bufferoo_details_avatar_image)
            }
        }
    }
}
