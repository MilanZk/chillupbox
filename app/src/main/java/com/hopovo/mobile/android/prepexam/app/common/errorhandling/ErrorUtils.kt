package com.hopovo.mobile.android.prepexam.app.common.errorhandling

import android.content.Context
import com.hopovo.mobile.android.prepexam.app.R

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