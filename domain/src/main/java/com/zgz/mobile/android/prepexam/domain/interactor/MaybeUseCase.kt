package com.zgz.mobile.android.prepexam.domain.interactor

import com.zgz.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.zgz.mobile.android.prepexam.domain.executor.ThreadExecutor
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
     *
     * This function is open in order to be mockeable in instrumental tests, which do not allow to mock final classes
     * or functions.
     */
    open fun execute(params: Params? = null): Maybe<T> {
        return this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }
}