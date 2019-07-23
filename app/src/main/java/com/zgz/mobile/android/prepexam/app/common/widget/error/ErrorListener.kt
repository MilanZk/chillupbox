package com.zgz.mobile.android.prepexam.app.common.widget.error

import com.zgz.mobile.android.prepexam.app.common.errorhandling.ErrorBundle

interface ErrorListener {

    fun onRetry(errorBundle: ErrorBundle)
}