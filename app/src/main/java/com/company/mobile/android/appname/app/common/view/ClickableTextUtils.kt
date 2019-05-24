package com.company.mobile.android.appname.app.common.view

import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

object ClickableTextUtils {

    fun makeTextViewClickable(textView: TextView, textColor: Int, clickActionFactory: ClickActionFactory) {
        textView.movementMethod = LinkMovementMethod.getInstance()
        val spans = textView.text as Spannable
        val clickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                clickActionFactory.onClickAction(widget)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = textColor
            }
        }
        spans.setSpan(clickSpan, 0, spans.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}