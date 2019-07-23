package com.zgz.mobile.android.prepexam.app.common.widget.empty

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.zgz.mobile.android.prepexam.app.R
import kotlinx.android.synthetic.main.view_empty.view.bt_empty_view_retry_button

/**
 * Widget used to display an empty state to the user
 */
class EmptyView : RelativeLayout {

    var emptyListener: EmptyListener? = null

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
        LayoutInflater.from(context).inflate(R.layout.view_empty, this)
        bt_empty_view_retry_button.setOnClickListener { emptyListener?.onCheckAgainClicked() }
    }
}