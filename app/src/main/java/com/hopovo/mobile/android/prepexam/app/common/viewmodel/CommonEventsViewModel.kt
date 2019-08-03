package com.hopovo.mobile.android.prepexam.app.common.viewmodel

import androidx.lifecycle.ViewModel
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.CommonEvent.Unauthorized
import com.hopovo.mobile.android.prepexam.model.exception.HTTPException
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

abstract class CommonEventsViewModel : ViewModel() {

    lateinit var commonLiveEvent: SingleLiveEvent<CommonEvent>

    abstract class CompletableRemoteInterceptor(private val commonLiveEvent: SingleLiveEvent<CommonEvent>) : DisposableCompletableObserver() {

        abstract override fun onComplete()

        override fun onError(e: Throwable) {
            when (e) {
                is HTTPException -> {
                    val errorCode = e.statusCode
                    if (errorCode == 401) {
                        commonLiveEvent.value = Unauthorized
                    } else {
                        onRegularError(e)
                    }
                }
                else -> onRegularError(e)
            }
        }

        abstract fun onRegularError(e: Throwable)
    }

    abstract class SingleRemoteInterceptor<R>(private val commonLiveEvent: SingleLiveEvent<CommonEvent>) : DisposableSingleObserver<R>() {

        abstract override fun onSuccess(t: R)

        override fun onError(e: Throwable) {
            when (e) {
                is HTTPException -> {
                    val errorCode = e.statusCode
                    if (errorCode == 401) {
                        commonLiveEvent.value = Unauthorized
                    } else {
                        onRegularError(e)
                    }
                }
                else -> onRegularError(e)
            }
        }

        abstract fun onRegularError(e: Throwable)
    }
}