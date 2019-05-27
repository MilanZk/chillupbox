@file:JvmMultifileClass

package com.company.mobile.android.appname.app.common.view

import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

// See: https://phauer.com/2017/idiomatic-kotlin-best-practices/#top-level-extension-functions-for-utility-functions
fun TextView.makeClickable(textColor: Int, clickActionFactory: ClickActionFactory) {
    this.movementMethod = LinkMovementMethod.getInstance()
    val spans = this.text as Spannable
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
