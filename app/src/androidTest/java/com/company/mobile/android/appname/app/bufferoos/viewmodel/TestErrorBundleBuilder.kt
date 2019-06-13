package com.company.mobile.android.appname.app.bufferoos.viewmodel

import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.errorhandling.AppAction
import com.company.mobile.android.appname.app.common.errorhandling.AppError
import com.company.mobile.android.appname.app.common.errorhandling.ErrorBundle
import com.company.mobile.android.appname.app.common.errorhandling.ErrorBundleBuilder

/**
 * Factory used to create error bundles from an Exception as source.
 */
class TestErrorBundleBuilder : ErrorBundleBuilder {

    override fun build(throwable: Throwable, appAction: AppAction): ErrorBundle {
        val message = throwable.message?.let {
            if (it.isNotEmpty()) it
            else "There was an application error"
        } ?: "There was an application error"

        // Unwrap RuntimeExceptions, wrapped by emitter.tryOnError()
        val t = if (throwable is RuntimeException && throwable.cause != null && throwable.cause is Exception) throwable.cause as Exception else throwable

        val stringId = R.string.error_default_message

        val appError = AppError.UNKNOWN

        return ErrorBundle(t, stringId, message, appAction, appError)
    }
}
