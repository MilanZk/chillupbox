package com.hopovo.mobile.android.prepexam.app.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.BaseFragment
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppAction
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppAction.SIGN_IN
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppError.NO_INTERNET
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppError.TIMEOUT
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundle
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorDialogFragment
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorDialogFragment.ErrorDialogFragmentListener
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorUtils
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.*
import com.hopovo.mobile.android.prepexam.app.common.navigation.Navigator
import com.hopovo.mobile.android.prepexam.app.common.validation.EditTextRegexValidator
import com.hopovo.mobile.android.prepexam.app.common.validation.EditTextRequiredInputValidator
import com.hopovo.mobile.android.prepexam.app.common.validation.EditTextUtils
import com.hopovo.mobile.android.prepexam.app.common.view.ClickActionFactory
import com.hopovo.mobile.android.prepexam.app.common.view.makeClickable
import com.hopovo.mobile.android.prepexam.app.common.view.showNotAvailableYetMessage
import com.hopovo.mobile.android.prepexam.app.common.widget.error.ErrorListener
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SignInFragment : BaseFragment(), ErrorDialogFragmentListener {

    companion object {

        private const val ERROR_DIALOG_REQUEST_CODE = 0

        fun newInstance() = SignInFragment()
    }

    private val signInViewModel: SignInViewModel by sharedViewModel()

    //region Lifecycle methods
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
        btn_sign_in_button.setOnClickListener {
            signIn()
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
                if (it != null) handleDataState(it)
            }
        )
    }
    //endregion

    //region Sign in
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

    private fun signIn() {
        activity?.let { activity ->
            Navigator.navigateToMainActivity(activity)
            activity.finish()
        }
        btn_sign_in_button.isEnabled = false

        if (isValidInformation()) {
            val username = tiet_sign_in_username.text.toString()
            val password = tiet_sign_in_password.text.toString()

            Timber.d("Sign in user \'%s\' with password \'%s\'.", username, password)
            signInViewModel.signIn(username, password)
        } else {
            btn_sign_in_button.isEnabled = true
        }
    }
    //endregion

    //region Handling state
    private fun handleDataState(signInState: SignInState) {
        when (signInState) {
            is Loading -> setupScreenForLoadingState()
            is Success -> setupScreenForSuccess(signInState.data)
            is Error -> setupScreenForError(signInState.errorBundle)
        }
        btn_sign_in_button?.apply {
            isEnabled = true
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
                Navigator.navigateToMainActivity(activity)
                activity.finish()
            }
        } else {
            Timber.w("User id is empty")
            Snackbar.make(btn_sign_in_button, R.string.sign_in_no_user_id, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupScreenForError(errorBundle: ErrorBundle) {
        lv_sign_in_loading_view.visibility = View.GONE

        if (errorBundle.appError == NO_INTERNET || errorBundle.appError == TIMEOUT) {
            // Example of using a custom error view as part of the fragment view
            showErrorView(errorBundle)
        } else {
            // Example of using an error fragment dialog
            showErrorDialog(errorBundle)
        }
    }
    //endregion

    //region Error and empty views
    private fun setupViewListeners() {
        ev_sign_in_error_view.errorListener = errorListener
    }

    private val errorListener = object : ErrorListener {
        override fun onRetry(errorBundle: ErrorBundle) {
            retry(errorBundle.appAction)
        }
    }

    private fun retry(appAction: AppAction) {
        when (appAction) {
            SIGN_IN -> signIn()
            else -> Timber.e("Unknown action code")
        }
    }

    private fun showErrorView(errorBundle: ErrorBundle) {
        activity?.let { activity ->
            ev_sign_in_error_view.visibility = View.VISIBLE
            ev_sign_in_error_view.errorBundle = errorBundle
            ev_sign_in_error_view.setErrorMessage(ErrorUtils.buildErrorMessageForDialog(activity, errorBundle).message)
        } ?: Timber.e("Activity is null")
    }

    private fun showErrorDialog(errorBundle: ErrorBundle) {
        val tag = ErrorDialogFragment.TAG
        activity?.let { activity ->
            activity.supportFragmentManager?.let { fragmentManager ->
                val previousDialogFragment = fragmentManager.findFragmentByTag(tag) as? DialogFragment

                // Check that error dialog is not already shown after a screen rotation
//                if (previousDialogFragment != null
//                    && previousDialogFragment.dialog != null
//                    && previousDialogFragment.dialog.isShowing
//                    && !previousDialogFragment.isRemoving
//                ) {
//                    // Error dialog is shown
//                    Timber.w("Error dialog is already shown")
//                } else {
//                    // Error dialog is not shown
//                    val errorDialogFragment = ErrorDialogFragment.newInstance(
//                        ErrorUtils.buildErrorMessageForDialog(activity, errorBundle),
//                        true
//                    )
//                    if (!fragmentManager.isDestroyed && !fragmentManager.isStateSaved) {
//                        // Sets the target fragment for using later when sending results
//                        errorDialogFragment.setTargetFragment(this@SignInFragment, ERROR_DIALOG_REQUEST_CODE)
//                        // Fragment contains the dialog and dialog should be controlled from fragment interface.
//                        // See: https://stackoverflow.com/a/8921129/5189200
//                        errorDialogFragment.isCancelable = false
//                        errorDialogFragment.show(fragmentManager, tag)
//                    }
//                }
            } ?: Timber.e("Support fragment manager is null")
        } ?: Timber.e("Activity is null")
    }

    override fun onErrorDialogAccepted(action: Long, retry: Boolean) {
        if (retry) {
            retry(AppAction.fromCode(action))
        } else {
            Timber.d("There was no retry option for action \'$action\' given to the user.")
        }
    }

    override fun onErrorDialogCancelled(action: Long) {
        Timber.d("User cancelled error dialog")
    }
    //endregion
}