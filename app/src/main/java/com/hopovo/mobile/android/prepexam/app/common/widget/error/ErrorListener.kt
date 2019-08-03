package com.hopovo.mobile.android.prepexam.app.common.widget.error

import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundle

interface ErrorListener {

    fun onRetry(errorBundle: ErrorBundle)
}