package com.company.mobile.android.appname.app.common.errorhandling

interface ErrorBundleBuilder {

    fun build(throwable: Throwable, appAction: AppAction): ErrorBundle
}
