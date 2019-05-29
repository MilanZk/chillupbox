package com.company.mobile.android.appname.app

import android.app.Application
import com.company.mobile.android.appname.app.di.applicationModule
import com.company.mobile.android.appname.app.di.bufferoosModule
import com.company.mobile.android.appname.app.di.mainModule
import com.company.mobile.android.appname.app.di.signInModule
import com.company.mobile.android.appname.app.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class BufferooApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@BufferooApplication)
            // modules
            modules(listOf(applicationModule, splashModule, signInModule, mainModule, bufferoosModule))
        }

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
