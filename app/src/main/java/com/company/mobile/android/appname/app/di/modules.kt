package com.company.mobile.android.appname.app.di

import androidx.room.Room
import com.company.mobile.android.appname.app.BuildConfig
import com.company.mobile.android.appname.app.UiThread
import com.company.mobile.android.appname.app.browse.BrowseAdapter
import com.company.mobile.android.appname.app.browse.BrowseBufferoosViewModel
import com.company.mobile.android.appname.data.BufferooDataRepository
import com.company.mobile.android.appname.data.browse.interactor.GetBufferoos
import com.company.mobile.android.appname.data.executor.JobExecutor
import com.company.mobile.android.appname.data.executor.PostExecutionThread
import com.company.mobile.android.appname.data.executor.ThreadExecutor
import com.company.mobile.android.appname.data.repository.BufferooRepository
import com.company.mobile.android.appname.data.source.BufferooDataStore
import com.company.mobile.android.appname.data.source.BufferooDataStoreFactory
import com.company.mobile.android.appname.datasources.cache.BufferooCacheImpl
import com.company.mobile.android.appname.datasources.cache.PreferencesHelper
import com.company.mobile.android.appname.datasources.cache.db.BufferoosDatabase
import com.company.mobile.android.appname.datasources.cache.mapper.BufferooEntityMapper
import com.company.mobile.android.appname.datasources.remote.BufferooRemoteImpl
import com.company.mobile.android.appname.datasources.remote.BufferooServiceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val applicationModule = module(override = true) {

    single { PreferencesHelper(androidContext()) }

    factory { com.company.mobile.android.appname.datasources.remote.mapper.BufferooEntityMapper() }

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

    factory<BufferooDataStore>("remote") { BufferooRemoteImpl(get(), get()) }
    factory<BufferooDataStore>("local") { BufferooCacheImpl(get(), get(), get()) }
    factory { BufferooDataStoreFactory(get("local"), get("remote")) }

    factory { BufferooEntityMapper() }
    factory { BufferooServiceFactory.makeBuffeooService(BuildConfig.DEBUG) }

    factory<BufferooRepository> { BufferooDataRepository(get()) }
}

val browseModule = module("Browse", override = true) {
    factory { BrowseAdapter() }
    factory { GetBufferoos(get(), get(), get()) }
    viewModel { BrowseBufferoosViewModel(get()) }
}