package com.company.mobile.android.appname.app.common.exception

import android.content.Context
import com.company.mobile.android.appname.app.R

object ErrorUtils {

    fun buildErrorMessageForDialog(context: Context, errorBundle: ErrorBundle): ErrorMessageForDialog {
        return ErrorMessageForDialog(
            context.getString(R.string.error_dialog_title),
            context.getString(errorBundle.stringId),
            errorBundle.debugMessage,
            errorBundle.appAction.code,
            errorBundle.appError.code
        )
    }
}