package com.hopovo.mobile.android.prepexam.data.exercise.repository

import com.hopovo.mobile.android.prepexam.data.exercise.source.ExerciseDataStoreFactory
import com.hopovo.mobile.android.prepexam.domain.exercise.repository.ExerciseRepository
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Completable
import io.reactivex.Observable

class ExerciseDataRepository(private val factory: ExerciseDataStoreFactory) : ExerciseRepository {
    override fun getExerciseById(id: Int): Observable<Exercise> {
        return factory.retrieveCacheDataStore().getExerciseById(id)
    }

    override fun getExercises(): Observable<List<Exercise>> {
        return factory.retrieveCacheDataStore().getExercise()
    }

    override fun saveExercise(exercise: Exercise): Completable {
        return factory.retrieveCacheDataStore().saveExercise(exercise)
    }
}