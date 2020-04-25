package com.hopovo.mobile.android.prepexam.app.common

import android.view.View
import android.view.View.*

fun View.gone() {
    this.visibility = GONE
}

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}