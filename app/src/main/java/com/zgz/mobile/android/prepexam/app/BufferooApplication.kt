package com.zgz.mobile.android.prepexam.app

import android.app.Application
import com.zgz.mobile.android.prepexam.app.di.applicationModule
import com.zgz.mobile.android.prepexam.app.di.bufferoosModule
import com.zgz.mobile.android.prepexam.app.di.mainModule
import com.zgz.mobile.android.prepexam.app.di.signInModule
import com.zgz.mobile.android.prepexam.app.di.splashModule
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
