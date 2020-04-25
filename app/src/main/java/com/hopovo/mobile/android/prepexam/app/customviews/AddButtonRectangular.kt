package com.hopovo.mobile.android.prepexam.app.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.hopovo.mobile.android.prepexam.app.R

class AddButtonRectangular @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    init {
        LayoutInflater.from(context).inflate(R.layout.add_button_rectangular, this, true)
    }
}