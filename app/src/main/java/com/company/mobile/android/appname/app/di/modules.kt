package com.company.mobile.android.appname.app.di

import androidx.room.Room
import com.company.mobile.android.appname.app.BuildConfig
import com.company.mobile.android.appname.app.UiThread
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosAdapter
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosViewModel
import com.company.mobile.android.appname.data.bufferoo.repository.BufferooDataRepository
import com.company.mobile.android.appname.data.bufferoo.source.BufferooDataStore
import com.company.mobile.android.appname.data.bufferoo.source.BufferooDataStoreFactory
import com.company.mobile.android.appname.datasources.bufferoo.cache.BufferooCacheImpl
import com.company.mobile.android.appname.datasources.bufferoo.cache.PreferencesHelper
import com.company.mobile.android.appname.datasources.bufferoo.cache.db.BufferoosDatabase
import com.company.mobile.android.appname.datasources.bufferoo.cache.mapper.BufferooEntityMapper
import com.company.mobile.android.appname.datasources.bufferoo.remote.BufferooRemoteImpl
import com.company.mobile.android.appname.datasources.bufferoo.remote.BufferooServiceFactory
import com.company.mobile.android.appname.domain.bufferoo.interactor.GetBufferoos
import com.company.mobile.android.appname.domain.bufferoo.repository.BufferooRepository
import com.company.mobile.android.appname.domain.executor.JobExecutor
import com.company.mobile.android.appname.domain.executor.PostExecutionThread
import com.company.mobile.android.appname.domain.executor.ThreadExecutor
import com.company.mobile.android.appname.app.main.navigationdrawer.NavigationDrawerMainActivityViewModel
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

    factory { com.company.mobile.android.appname.datasources.bufferoo.remote.mapper.BufferooEntityMapper() }

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
    factory<BufferooDataStore>(named("remoteBufferooDataStore")) { BufferooRemoteImpl(get(), get()) }
    factory<BufferooDataStore>(named("localBufferooDataStore")) { BufferooCacheImpl(get(), get(), get()) }
    factory { BufferooDataStoreFactory(get(named("localBufferooDataStore")), get(named("remoteBufferooDataStore"))) }

    factory { BufferooEntityMapper() }
    factory { BufferooServiceFactory.makeBuffeooService(BuildConfig.DEBUG) }

    factory<BufferooRepository> { BufferooDataRepository(get()) }
}

val mainModule = module(override = true) {
    viewModel { NavigationDrawerMainActivityViewModel() }
}

val bufferoosModule = module(override = true) {
    factory { BufferoosAdapter() }
    factory { GetBufferoos(get(), get(), get()) }
    viewModel { BufferoosViewModel(get()) }
}