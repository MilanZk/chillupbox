package com.company.mobile.android.appname.app.common.widget.error

import com.company.mobile.android.appname.app.common.exception.ErrorBundle

interface ErrorListener {

    fun onRetry(errorBundle: ErrorBundle)
}