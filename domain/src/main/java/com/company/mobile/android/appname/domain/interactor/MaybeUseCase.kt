package com.company.mobile.android.appname.domain.interactor

import com.company.mobile.android.appname.domain.executor.PostExecutionThread
import com.company.mobile.android.appname.domain.executor.ThreadExecutor
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Maybe].
 */
abstract class MaybeUseCase<T, in Params> constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {

    /**
     * Builds a [Maybe] which will be used when the current [MaybeUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Maybe<T>

    /**
     * Executes the current use case.
     */
    fun execute(params: Params? = null): Maybe<T> {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler)
    }
}