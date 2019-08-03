package com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor

import com.hopovo.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.interactor.SingleUseCase
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Single

open class GetExercises(
    private val bufferooRepository: BufferooRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Exercise>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<List<Exercise>> {
        return bufferooRepository.getBufferoos()
    }
}