package com.hopovo.mobile.android.prepexam.domain.exercise.interactor

import com.hopovo.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.interactor.CompletableUseCase
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Completable

open class SaveExercise(
        private val bufferooRepository: BufferooRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : CompletableUseCase<Exercise>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Exercise): Completable {
     return  bufferooRepository.saveExercise(params)
    }


}