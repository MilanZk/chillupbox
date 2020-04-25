package com.hopovo.mobile.android.prepexam.domain.exercise.interactor

import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.exercise.repository.ExerciseRepository
import com.hopovo.mobile.android.prepexam.domain.interactor.ObservableUseCase
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Observable

open class GetExercises(
        private val exerciseRepository: ExerciseRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Exercise>, Void>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Observable<List<Exercise>> {
        return exerciseRepository.getExercises()
    }
}