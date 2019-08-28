package com.hopovo.mobile.android.prepexam.app.di

import androidx.room.Room
import com.hopovo.mobile.android.prepexam.app.BuildConfig
import com.hopovo.mobile.android.prepexam.app.UiThread
import com.hopovo.mobile.android.prepexam.app.account.AccountErrorBundleBuilder
import com.hopovo.mobile.android.prepexam.app.account.SignInViewModel
import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundleBuilder
import com.hopovo.mobile.android.prepexam.app.exercise.master.ExerciseErrorBundleBuilder
import com.hopovo.mobile.android.prepexam.app.exercise.master.ExerciseListAdapter
import com.hopovo.mobile.android.prepexam.app.exercise.master.ExerciseViewModel
import com.hopovo.mobile.android.prepexam.app.splash.SplashActivityViewModel
import com.hopovo.mobile.android.prepexam.app.splash.SplashErrorBundleBuilder
import com.hopovo.mobile.android.prepexam.data.bufferoo.repository.ExerciseDataRepository
import com.hopovo.mobile.android.prepexam.data.bufferoo.source.BufferooDataStore
import com.hopovo.mobile.android.prepexam.data.bufferoo.source.BufferooDataStoreFactory
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.ExerciseCacheImpl
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.PreferencesHelper
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.BufferoosDatabase
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.mapper.BufferooEntityMapper
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.BufferooRemoteImpl
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.BufferooServiceFactory
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.mapper.ExerciseRemoteMapper
import com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor.GetCredentials
import com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor.GetExercises
import com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor.SignInBufferoos
import com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor.SignOutBufferoos
import com.hopovo.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.hopovo.mobile.android.prepexam.domain.executor.JobExecutor
import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.exercise.interactor.SaveExercise
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

    factory { ExerciseRemoteMapper() }

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    single {
        Room.databaseBuilder(
                androidContext(),
                BufferoosDatabase::class.java, "exercises.db"
        )
                .build()
    }
    factory { get<BufferoosDatabase>().cachedBufferooDao() }

    // IMPORTANT: Named qualifiers must be unique inside the module
    factory<BufferooDataStore>(named("remoteBufferooDataStore")) { BufferooRemoteImpl(get(), get(), androidContext()) }
    factory<BufferooDataStore>(named("localBufferooDataStore")) { ExerciseCacheImpl(get(), get(), get()) }
    factory { BufferooDataStoreFactory(get(named("localBufferooDataStore")), get(named("remoteBufferooDataStore"))) }

    factory { BufferooEntityMapper() }
    factory { BufferooServiceFactory.makeBuffeooService(androidContext(), BuildConfig.DEBUG) }

    factory<BufferooRepository> { ExerciseDataRepository(get()) }
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
}

val exerciseListModule = module(override = true) {
    factory { ExerciseListAdapter() }
    factory { GetExercises(get(), get(), get()) }
    factory { SaveExercise(get(), get(), get()) }
    factory<ErrorBundleBuilder>(named("bufferoosErrorBundleBuilder")) { ExerciseErrorBundleBuilder() }
    viewModel { ExerciseViewModel(get(), get(named("bufferoosErrorBundleBuilder")), get()) }
}