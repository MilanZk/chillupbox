package com.zgz.mobile.android.prepexam.app.choosenavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zgz.mobile.android.prepexam.app.R
import com.zgz.mobile.android.prepexam.app.common.BaseFragment
import com.zgz.mobile.android.prepexam.app.common.navigation.Navigator
import com.zgz.mobile.android.prepexam.app.common.view.showNotAvailableYetMessage
import kotlinx.android.synthetic.main.fragment_choose_navigation.*

class ChooseNavigationFragment : BaseFragment() {

    companion object {
        fun newInstance() = ChooseNavigationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_choose_navigation, container, false)
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        super.initializeViews(savedInstanceState)

        btn_choose_navigation_navigation_drawer_button.setOnClickListener {
            activity?.let { activity ->
                Navigator.navigateToNavigationDrawerMainActivity(activity)
                activity.finish()
            }
        }

        btn_choose_navigation_bottom_navigation_button.setOnClickListener {
            btn_choose_navigation_bottom_navigation_button.showNotAvailableYetMessage()
        }

        btn_choose_navigation_no_navigation_button.setOnClickListener {
            activity?.let { activity ->
                Navigator.navigateToNoNavigationActivity(activity)
                activity.finish()
            }
        }
    }
}