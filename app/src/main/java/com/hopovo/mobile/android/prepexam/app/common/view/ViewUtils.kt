@file:JvmMultifileClass

package com.hopovo.mobile.android.prepexam.app.common.view

import android.view.View
import com.hopovo.mobile.android.prepexam.app.R
import com.google.android.material.snackbar.Snackbar

// See: https://phauer.com/2017/idiomatic-kotlin-best-practices/#top-level-extension-functions-for-utility-functions
fun View.showNotAvailableYetMessage() {
    Snackbar.make(
        this,
        R.string.not_available_yet,
        Snackbar.LENGTH_SHORT
    ).show()
}
