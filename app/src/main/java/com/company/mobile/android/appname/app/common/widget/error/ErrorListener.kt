package com.company.mobile.android.appname.app.common.widget.error

import com.company.mobile.android.appname.app.common.errorhandling.ErrorBundle

interface ErrorListener {

    fun onRetry(errorBundle: ErrorBundle)
}