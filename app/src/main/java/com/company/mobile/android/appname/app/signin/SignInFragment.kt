package com.company.mobile.android.appname.app.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseFragment
import com.company.mobile.android.appname.app.common.navigation.Navigator
import com.company.mobile.android.appname.app.common.view.ClickActionFactory
import com.company.mobile.android.appname.app.common.view.ClickableTextUtils
import com.company.mobile.android.appname.app.common.view.ViewUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_basic.fl_basic_main_container
import kotlinx.android.synthetic.main.fragment_choose_navigation.btn_choose_navigation_bottom_navigation_button
import kotlinx.android.synthetic.main.fragment_choose_navigation.btn_choose_navigation_navigation_drawer_button
import kotlinx.android.synthetic.main.fragment_choose_navigation.btn_choose_navigation_no_navigation_button
import kotlinx.android.synthetic.main.fragment_choose_navigation.cl_choose_navigation_container
import kotlinx.android.synthetic.main.fragment_sign_in.btn_sign_in_next
import kotlinx.android.synthetic.main.fragment_sign_in.tv_sign_in_click_here
import timber.log.Timber

class SignInFragment : BaseFragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        super.initializeViews(savedInstanceState)

        btn_sign_in_next.setOnClickListener {
            activity?.let { activity ->
                Navigator.navigateToChooseNavigationActivity(activity)
                activity.finish()
            }
        }

        val color = resources.getColor(R.color.colorPrimaryLinkText)
        ClickableTextUtils.makeTextViewClickable(tv_sign_in_click_here, color, object : ClickActionFactory {
            override fun onClickAction(widget: View) {
                ViewUtils.showNotAvailableYetMessage(tv_sign_in_click_here)
            }
        })
    }
}