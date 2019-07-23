package com.zgz.mobile.android.prepexam.app.di

import androidx.room.Room
import com.zgz.mobile.android.prepexam.app.BuildConfig
import com.zgz.mobile.android.prepexam.app.UiThread
import com.zgz.mobile.android.prepexam.app.account.AccountErrorBundleBuilder
import com.zgz.mobile.android.prepexam.app.account.SignInViewModel
import com.zgz.mobile.android.prepexam.app.bufferoos.master.BufferoosAdapter
import com.zgz.mobile.android.prepexam.app.bufferoos.viewmodel.BufferoosErrorBundleBuilder
import com.zgz.mobile.android.prepexam.app.bufferoos.viewmodel.BufferoosViewModel
import com.zgz.mobile.android.prepexam.app.common.errorhandling.ErrorBundleBuilder
import com.zgz.mobile.android.prepexam.app.main.navigationdrawer.NavigationDrawerMainActivityViewModel
import com.zgz.mobile.android.prepexam.app.main.nonavigation.NoNavigationMainActivityViewModel
import com.zgz.mobile.android.prepexam.app.splash.SplashActivityViewModel
import com.zgz.mobile.android.prepexam.app.splash.SplashErrorBundleBuilder
import com.zgz.mobile.android.prepexam.data.bufferoo.repository.BufferooDataRepository
import com.zgz.mobile.android.prepexam.data.bufferoo.source.BufferooDataStore
import com.zgz.mobile.android.prepexam.data.bufferoo.source.BufferooDataStoreFactory
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.BufferooCacheImpl
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.PreferencesHelper
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.db.BufferoosDatabase
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.mapper.BufferooEntityMapper
import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.BufferooRemoteImpl
import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.BufferooServiceFactory
import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.GetBufferoos
import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.GetCredentials
import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.SignInBufferoos
import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.SignOutBufferoos
import com.zgz.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.zgz.mobile.android.prepexam.domain.executor.JobExecutor
import com.zgz.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.zgz.mobile.android.prepexam.domain.executor.ThreadExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * This file is where all Koin modules are defined.
 *
 * [The original template](https://github.com/bufferapp/clean-architecture-koin-boilerplate) was a
 * port of a [Dagger template](https://github.com/bufferapp/android-clean-architecture-boilerplate),
 * and the explanation of the dependency injection porting was explained in this
 * [post](https://overflow.buffer.com/2018/09/13/a-brief-look-at-koin-on-android/).
 */
val applicationModule = module(override = true) {

    single { PreferencesHelper(androidContext()) }

    factory { com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.mapper.BufferooEntityMapper() }

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    single {
        Room.databaseBuilder(
            androidContext(),
            BufferoosDatabase::class.java, "bufferoos.db"
        )
            .build()
    }
    factory { get<BufferoosDatabase>().cachedBufferooDao() }

    // IMPORTANT: Named qualifiers must be unique inside the module
    factory<BufferooDataStore>(named("remoteBufferooDataStore")) { BufferooRemoteImpl(get(), get(), androidContext()) }
    factory<BufferooDataStore>(named("localBufferooDataStore")) { BufferooCacheImpl(get(), get(), get()) }
    factory { BufferooDataStoreFactory(get(named("localBufferooDataStore")), get(named("remoteBufferooDataStore"))) }

    factory { BufferooEntityMapper() }
    factory { BufferooServiceFactory.makeBuffeooService(androidContext(), BuildConfig.DEBUG) }

    factory<BufferooRepository> { BufferooDataRepository(get()) }
}

val splashModule = module(override = true) {
    factory { GetCredentials(get(), get(), get()) }
    factory<ErrorBundleBuilder>(named("splashErrorBundleBuilder")) { SplashErrorBundleBuilder() }
    viewModel { SplashActivityViewModel(get(), get(named("splashErrorBundleBuilder"))) }
}

val signInModule = module(override = true) {
    factory { SignInBufferoos(get(), get(), get()) }
    factory<ErrorBundleBuilder>(named("signInErrorBundleBuilder")) { AccountErrorBundleBuilder() }
    viewModel { SignInViewModel(get(), get(named("signInErrorBundleBuilder"))) }
}

val mainModule = module(override = true) {
    factory { SignOutBufferoos(get(), get(), get()) }
    factory<ErrorBundleBuilder>(named("mainErrorBundleBuilder")) { AccountErrorBundleBuilder() }
    viewModel { NavigationDrawerMainActivityViewModel(get(), get(named("mainErrorBundleBuilder"))) }
    viewModel { NoNavigationMainActivityViewModel(get(), get(named("mainErrorBundleBuilder"))) }
}

val bufferoosModule = module(override = true) {
    factory { BufferoosAdapter() }
    factory { GetBufferoos(get(), get(), get()) }
    factory<ErrorBundleBuilder>(named("bufferoosErrorBundleBuilder")) { BufferoosErrorBundleBuilder() }
    viewModel { BufferoosViewModel(get(), get(named("bufferoosErrorBundleBuilder"))) }
}