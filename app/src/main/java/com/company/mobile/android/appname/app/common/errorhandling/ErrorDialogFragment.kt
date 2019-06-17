package com.company.mobile.android.appname.app.common.errorhandling

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.util.ResourceUtils
import timber.log.Timber

class ErrorDialogFragment : DialogFragment() {
    private var accepted = false
    private var cancelled = false
    private lateinit var errorMessageForDialog: ErrorMessageForDialog
    private var retry = false

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     */
    interface ErrorDialogFragmentListener {

        fun onErrorDialogAccepted(action: Long, retry: Boolean)
        fun onErrorDialogCancelled(action: Long)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments
        if (args != null) {
            errorMessageForDialog = args.getParcelable(MESSAGE_KEY)!!
            retry = args.getBoolean(RETRY_KEY, false)
        } else {
            Timber.e("Arguments cannot be null")
        }

        // Build message with styled support code text
        val colorString = ResourceUtils.getColorString(activity!!, R.color.colorPrimaryTextDisabled)
        val errorCode = getString(R.string.error_support_code, errorMessageForDialog.actionCode.toString() + "/" + errorMessageForDialog.errorCode)
        val styledMessage = errorMessageForDialog.message + "<br><br>" +
                String.format("<font color=\"#%s\">%s</font>", colorString, errorCode)

        val builder = AlertDialog.Builder(ContextThemeWrapper(activity, R.style.customErrorAlertDialog))
        val acceptStringId = if (retry) R.string.retry else R.string.ok
        builder.setTitle(errorMessageForDialog.title)
            .setMessage(HtmlCompat.fromHtml(styledMessage, HtmlCompat.FROM_HTML_MODE_LEGACY))
            .setCancelable(true)
            .setPositiveButton(acceptStringId) { _: DialogInterface, _: Int ->
                // dialog: DialogInterface, buttonId: Int
                accepted = true
                sendResultBack()
            }
        if (retry) {
            builder
                .setNegativeButton(R.string.cancel) { _: DialogInterface, _: Int ->
                    // dialog: DialogInterface, buttonId: Int
                    cancelled = true
                    sendResultBack()
                }
        }

        // Create the AlertDialog object and return it
        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        sendResultBack()
    }

    // Call this method to send the data back to the parent fragment
    private fun sendResultBack() {

        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        (targetFragment as? ErrorDialogFragmentListener)?.let { listener ->
            when {
                accepted -> listener.onErrorDialogAccepted(errorMessageForDialog.actionCode, retry)
                cancelled -> {
                    listener.onErrorDialogCancelled(errorMessageForDialog.actionCode)
                    dialog.cancel()
                }
                else -> // The used has dismissed the dialog with the back button or clicking outside the dialog
                    listener.onErrorDialogCancelled(errorMessageForDialog.actionCode)
            }
        } ?: throw ClassCastException("ErrorDialogFragment::sendResultBack: Parent fragment " + targetFragment.toString() + " must implement " + ErrorDialogFragmentListener::class.java.canonicalName)
    }

    companion object {

        val TAG = ErrorDialogFragment::class.java.simpleName

        private const val MESSAGE_KEY = "message"
        private const val RETRY_KEY = "retry"

        fun newInstance(errorMessageForDialog: ErrorMessageForDialog, retry: Boolean): ErrorDialogFragment {
            val errorDialogFragment = ErrorDialogFragment()
            val args = Bundle()
            args.putParcelable(MESSAGE_KEY, errorMessageForDialog)
            args.putBoolean(RETRY_KEY, retry)
            errorDialogFragment.arguments = args
            return errorDialogFragment
        }
    }
}
