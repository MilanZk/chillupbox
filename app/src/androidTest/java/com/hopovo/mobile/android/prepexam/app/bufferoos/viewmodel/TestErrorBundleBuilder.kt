package com.hopovo.mobile.android.prepexam.app.bufferoos.viewmodel

import com.hopovo.mobile.android.prepexam.app.R
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppAction
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.AppError
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundle
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundleBuilder

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
