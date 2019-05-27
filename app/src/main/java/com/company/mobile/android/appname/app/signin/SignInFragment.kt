package com.company.mobile.android.appname.app.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseFragment
import com.company.mobile.android.appname.app.common.navigation.Navigator
import com.company.mobile.android.appname.app.common.validation.EditTextRegexValidator
import com.company.mobile.android.appname.app.common.validation.EditTextRequiredInputValidator
import com.company.mobile.android.appname.app.common.validation.EditTextUtils
import com.company.mobile.android.appname.app.common.view.ClickActionFactory
import com.company.mobile.android.appname.app.common.view.makeClickable
import com.company.mobile.android.appname.app.common.view.showNotAvailableYetMessage
import kotlinx.android.synthetic.main.fragment_sign_in.btn_sign_in_next
import kotlinx.android.synthetic.main.fragment_sign_in.tiet_sign_in_password
import kotlinx.android.synthetic.main.fragment_sign_in.tiet_sign_in_username
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

        // Sign in button
        btn_sign_in_next.setOnClickListener {
            btn_sign_in_next.isEnabled = false

            if (isValidInformation()) {
                val username = tiet_sign_in_username.text.toString()
                val password = tiet_sign_in_password.text.toString()

                Timber.d("Sign in user \'%s\' with password \'%s\'.", username, password)
                activity?.let { activity ->
                    Navigator.navigateToChooseNavigationActivity(activity)
                    activity.finish()
                }
            } else {
                btn_sign_in_next.isEnabled = true
            }
        }

        // Click here text
        context?.let { context ->
            val color = ContextCompat.getColor(context, R.color.colorPrimaryLinkText)
            tv_sign_in_click_here.makeClickable(color, object : ClickActionFactory {
                override fun onClickAction(widget: View) {
                    tv_sign_in_click_here.showNotAvailableYetMessage()
                }
            })
        } ?: Timber.e("Context is null!")
    }

    private fun isValidInformation(): Boolean {
        // All errors are checked:
        // 1) Test all fields independently, but cascading the result (field_check && valid)
        // 2) For each field, check conditions from more to less priority. That is, if a given field
        // cannot be empty and must match a regular expression, check first for empty field and then for
        // the regular expression.
        var valid = EditTextUtils.isValid(
            EditTextRequiredInputValidator(tiet_sign_in_username, R.string.error_empty),
            EditTextRegexValidator(tiet_sign_in_username, EditTextUtils.NAME_REGULAR_EXPRESSION, R.string.error_invalid_username)
        )
        valid = EditTextUtils.isValid(
            EditTextRequiredInputValidator(tiet_sign_in_password, R.string.error_empty),
            EditTextRegexValidator(tiet_sign_in_password, EditTextUtils.PASSWORD_REGULAR_EXPRESSION, R.string.error_invalid_password)
        ) && valid

        return valid
    }
}