package com.company.mobile.android.appname.app.choosenavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseFragment
import com.company.mobile.android.appname.app.common.navigation.Navigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_choose_navigation.btn_choose_navigation_bottom_navigation_button
import kotlinx.android.synthetic.main.fragment_choose_navigation.btn_choose_navigation_navigation_drawer_button
import kotlinx.android.synthetic.main.fragment_choose_navigation.btn_choose_navigation_no_navigation_button
import kotlinx.android.synthetic.main.fragment_choose_navigation.cl_choose_navigation_container

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
            Snackbar.make(
                cl_choose_navigation_container,
                R.string.not_available_yet,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        btn_choose_navigation_no_navigation_button.setOnClickListener {
            activity?.let { activity ->
                Navigator.navigateToNoNavigationActivity(activity)
                activity.finish()
            }
        }
    }
}