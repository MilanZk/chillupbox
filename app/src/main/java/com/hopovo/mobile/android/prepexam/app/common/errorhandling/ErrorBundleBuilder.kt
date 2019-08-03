package com.hopovo.mobile.android.prepexam.app.common.errorhandling

interface ErrorBundleBuilder {

    fun build(throwable: Throwable, appAction: AppAction): ErrorBundle
}
