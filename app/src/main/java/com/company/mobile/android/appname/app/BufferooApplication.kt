package com.company.mobile.android.appname.app

import android.app.Application
import com.company.mobile.android.appname.app.di.applicationModule
import com.company.mobile.android.appname.app.di.bufferoosModule
import com.company.mobile.android.appname.app.di.mainModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class BufferooApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule, mainModule, bufferoosModule))
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
