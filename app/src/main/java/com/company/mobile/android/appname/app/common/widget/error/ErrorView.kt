package com.company.mobile.android.appname.app.common.widget.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.errorhandling.ErrorBundle
import kotlinx.android.synthetic.main.view_error.view.bt_error_view_retry_button
import kotlinx.android.synthetic.main.view_error.view.tv_error_view_message
import timber.log.Timber

/**
 * Widget used to display an error state to the user
 */
class ErrorView : RelativeLayout {

    var errorListener: ErrorListener? = null
    var errorBundle: ErrorBundle? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        bt_error_view_retry_button.setOnClickListener {
            errorBundle?.let { errorBundle ->
                errorListener?.apply {
                    onRetry(errorBundle)
                } ?: Timber.e("Error listener is null")
            } ?: Timber.e("Error bundle is null")
        }
    }

    fun setErrorMessage(message: String): Boolean {
        var queuedTask = false

        // View are not nulls
        tv_error_view_message?.let {
            // Post a task to update the view
            tv_error_view_message.post {
                // When the task is executed, check that the view is still there
                tv_error_view_message?.let {
                    // Update the message
                    tv_error_view_message.text = message
                }
            }
            queuedTask = true
        } ?: Timber.d("There is no error view for setting message")

        return queuedTask
    }
}