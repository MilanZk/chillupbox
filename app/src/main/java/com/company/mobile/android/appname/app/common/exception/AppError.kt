package com.company.mobile.android.appname.app.common.exception

enum class AppError constructor(val code: Long) {
    UNKNOWN(-1L),
    NONE(0L),
    NO_INTERNET(1L),
    TIMEOUT(2L);

    override fun toString(): String {
        return java.lang.Long.toString(this.code)
    }

    companion object {

        fun fromCode(code: Long): AppError {
            var result = UNKNOWN

            val retryActions = values()
            var i = 0
            while (i < retryActions.size && result == UNKNOWN) {
                val retryAction = retryActions[i]
                if (retryAction.code == code) {
                    result = retryAction
                }
                ++i
            }

            return result
        }
    }
}
