package com.hopovo.mobile.android.prepexam.domain.exercise.interactor

import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.exercise.repository.ExerciseRepository
import com.hopovo.mobile.android.prepexam.domain.interactor.CompletableUseCase
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Completable

open class SaveExercise(
        private val exerciseRepository: ExerciseRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : CompletableUseCase<Exercise>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Exercise): Completable {
     return  exerciseRepository.saveExercise(params)
    }


}