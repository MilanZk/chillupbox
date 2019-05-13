package com.company.mobile.android.appname.app.common.widget.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.company.mobile.android.appname.app.R
import kotlinx.android.synthetic.main.view_error.view.bt_error_view_retry_button

/**
 * Widget used to display an empty state to the user
 */
class ErrorView : RelativeLayout {

    var errorListener: ErrorListener? = null

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
        bt_error_view_retry_button.setOnClickListener { errorListener?.onTryAgainClicked() }
    }
}