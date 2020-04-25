package com.hopovo.mobile.android.prepexam.app.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.hopovo.mobile.android.prepexam.app.R
import kotlinx.android.synthetic.main.word_edit_text.view.*

class WordEditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var titleText: String? = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.word_edit_text, this, true)
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.WordEditText,
                0, 0).apply {

            try {
                titleText = getString(R.styleable.WordEditText_word_title)
                setTitleText()
            } finally {
                recycle()
            }
        }
    }

    private fun setTitleText() {
        et_word_title.text = titleText
        invalidate()
        requestLayout()
    }
}