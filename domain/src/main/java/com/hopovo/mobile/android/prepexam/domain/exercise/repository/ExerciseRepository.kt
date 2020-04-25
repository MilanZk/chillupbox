package com.hopovo.mobile.android.prepexam.domain.exercise.repository

import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Completable
import io.reactivex.Observable

interface ExerciseRepository {
    fun saveExercise(exercise: Exercise): Completable

    fun getExerciseById(id: Int): Observable<Exercise>

    fun getExercises(): Observable<List<Exercise>>
}