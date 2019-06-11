package com.company.mobile.android.appname.app.common.exception

interface ErrorBundleBuilder {

    fun build(throwable: Throwable, appAction: AppAction): ErrorBundle
}
