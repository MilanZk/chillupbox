package com.company.mobile.android.appname.app.common.widget.loading

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.company.mobile.android.appname.app.BuildConfig
import com.company.mobile.android.appname.app.R
import kotlinx.android.synthetic.main.view_loading.view.tv_loading_view_message
import timber.log.Timber

/**
 * Widget used to display an loading state to the user
 */
class LoadView : RelativeLayout {

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
        LayoutInflater.from(context).inflate(R.layout.view_loading, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (BuildConfig.DEBUG) {
            tv_loading_view_message.visibility = View.VISIBLE
        }
    }

    fun updateLoadingMessage(message: String): Boolean {
        var queuedTask = false

        // View are not nulls
        tv_loading_view_message?.let {
            // Post a task to update the view
            tv_loading_view_message.post {
                // When the task is executed, check that the view is still there
                tv_loading_view_message?.let {
                    // Update the message
                    tv_loading_view_message.text = message
                }
            }
            queuedTask = true
        } ?: Timber.d("There is no loading view for updating message")

        return queuedTask
    }
}