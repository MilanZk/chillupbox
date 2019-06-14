package com.company.mobile.android.appname.app.common.validation

import android.widget.EditText

/**
 * Created by Daniel on 9/11/2015.
 */
class EditTextEmailValidator(editText: EditText, errorMessageId: Int) : EditTextValidator(editText, errorMessageId) {

    override val isValid: Boolean
        get() = if (EditTextUtils.testIsEmpty(editText)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(EditTextUtils.getText(editText)).matches()
        }
}
