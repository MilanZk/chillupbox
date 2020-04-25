package com.hopovo.mobile.android.prepexam.data.exercise.source

import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface ExerciseDataStore {

    fun saveExercise(exercise: Exercise): Completable

    fun getExerciseById(idExercise: Int): Observable<Exercise>

    fun getExercise(): Observable<List<Exercise>>

    fun isValidCache(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long)
}
