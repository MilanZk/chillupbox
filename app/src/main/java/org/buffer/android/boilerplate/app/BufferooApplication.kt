package org.buffer.android.boilerplate.app

import android.app.Application
import org.buffer.android.boilerplate.app.di.applicationModule
import org.buffer.android.boilerplate.app.di.browseModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class BufferooApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule, browseModule))
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
