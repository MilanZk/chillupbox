package com.hopovo.mobile.android.prepexam.app

import android.app.Application
import com.hopovo.mobile.android.prepexam.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PrepexamApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@PrepexamApplication)
            // modules
            modules(listOf(applicationModule, splashModule, signInModule, mainModule, exerciseListModule))
        }

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
