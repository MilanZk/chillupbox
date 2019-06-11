package com.company.mobile.android.appname.app.splash

import com.company.mobile.android.appname.app.R
import com.company.mobile.android.appname.app.common.exception.AppAction
import com.company.mobile.android.appname.app.common.exception.AppError
import com.company.mobile.android.appname.app.common.exception.ErrorBundle
import com.company.mobile.android.appname.app.common.exception.ErrorBundleBuilder
import com.company.mobile.android.appname.model.exception.HTTPException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import timber.log.Timber

/**
 * Factory used to create error bundles from an Exception as source.
 */
class SplashErrorBundleBuilder : ErrorBundleBuilder {

    override fun build(throwable: Throwable, appAction: AppAction): ErrorBundle {
        val message = throwable.message?.let {
            if (it.isNotEmpty()) it
            else "There was an application error"
        } ?: "There was an application error"

        // Unwrap RuntimeExceptions, wrapped by emitter.tryOnError()
        val t = if (throwable is RuntimeException && throwable.cause != null && throwable.cause is Exception) throwable.cause as Exception else throwable

        val stringId = when (t) {
            is UnknownHostException -> R.string.error_no_connection
            is TimeoutException -> R.string.error_connection_timeout
            else -> when (appAction) {
                AppAction.GET_CREDENTIALS -> if (t is HTTPException) {
                    // TODO: Handle HTTPExceptions here
                    R.string.error_get_credentials
                } else {
                    R.string.error_get_credentials
                }
                else -> {
                    Timber.e("Action \'%s\' not supported", appAction)
                    R.string.error_default_message
                }
            }
        }

        val appError = when (t) {
            is UnknownHostException -> AppError.NO_INTERNET
            is TimeoutException -> AppError.TIMEOUT
            else -> AppError.UNKNOWN
        }

        return ErrorBundle(t, stringId, message, appAction, appError)
    }
}
