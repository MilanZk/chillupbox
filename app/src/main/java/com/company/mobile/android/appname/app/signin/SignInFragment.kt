package com.company.mobile.android.appname.app.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.BaseFragment
import com.company.mobile.android.appname.app.common.model.ResourceState.Error
import com.company.mobile.android.appname.app.common.model.ResourceState.Loading
import com.company.mobile.android.appname.app.common.model.ResourceState.Success
import com.company.mobile.android.appname.app.common.navigation.Navigator
import com.company.mobile.android.appname.app.common.validation.EditTextRegexValidator
import com.company.mobile.android.appname.app.common.validation.EditTextRequiredInputValidator
import com.company.mobile.android.appname.app.common.validation.EditTextUtils
import com.company.mobile.android.appname.app.common.view.ClickActionFactory
import com.company.mobile.android.appname.app.common.view.makeClickable
import com.company.mobile.android.appname.app.common.view.showNotAvailableYetMessage
import com.company.mobile.android.appname.app.common.widget.error.ErrorListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sign_in.btn_sign_in_next
import kotlinx.android.synthetic.main.fragment_sign_in.ev_sign_in_error_view
import kotlinx.android.synthetic.main.fragment_sign_in.lv_sign_in_loading_view
import kotlinx.android.synthetic.main.fragment_sign_in.tiet_sign_in_password
import kotlinx.android.synthetic.main.fragment_sign_in.tiet_sign_in_username
import kotlinx.android.synthetic.main.fragment_sign_in.tv_sign_in_click_here
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SignInFragment : BaseFragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private val signInViewModel: SignInViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun earlyInitializeViews() {
        super.earlyInitializeViews()

        setupViewListeners()
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
                signInViewModel.signIn(username, password)
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

    override fun initializeContents(savedInstanceState: Bundle?) {
        super.initializeContents(savedInstanceState)

        // Link the fragment and the model view with "viewLifecycleOwner", so that observers
        // can be subscribed in onActivityCreated() and can be automatically unsubscribed
        // in onDestroyView().
        // IMPORTANT: Never use "this" as lifecycle owner.
        // See: https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
        signInViewModel.getSignIn().observe(viewLifecycleOwner,
            Observer<SignInState> {
                if (it != null) this.handleDataState(it)
            }
        )
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

    private fun handleDataState(signInState: SignInState) {
        when (signInState) {
            is Loading -> setupScreenForLoadingState()
            is Success -> setupScreenForSuccess(signInState.data)
            is Error -> setupScreenForError(signInState.message)
        }
    }

    private fun setupScreenForLoadingState() {
        lv_sign_in_loading_view.visibility = View.VISIBLE
        ev_sign_in_error_view.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: String) {
        ev_sign_in_error_view.visibility = View.GONE
        lv_sign_in_loading_view.visibility = View.GONE
        if (data.isNotEmpty()) {
            Timber.d("User id is: $data")

            activity?.let { activity ->
                Navigator.navigateToChooseNavigationActivity(activity)
                activity.finish()
            }
        } else {
            Timber.w("User id is empty")
            Snackbar.make(btn_sign_in_next, R.string.sign_in_no_user_id, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupScreenForError(message: String?) {
        lv_sign_in_loading_view.visibility = View.GONE
        ev_sign_in_error_view.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        ev_sign_in_error_view.errorListener = errorListener
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            btn_sign_in_next.showNotAvailableYetMessage()
        }
    }
}