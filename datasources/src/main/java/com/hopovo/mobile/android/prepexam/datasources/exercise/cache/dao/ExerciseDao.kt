package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.ExerciseDbModel
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.constants.ExerciseConstants
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class ExerciseDao {

    @Query(ExerciseConstants.QUERY_EXERCISE)
    abstract fun getExercises(): Observable<List<ExerciseDbModel>>

    @Query(ExerciseConstants.DELETE_ALL_EXERCISE)
    abstract fun clearExercise(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertExercise(exerciseDbModel: ExerciseDbModel): Completable

    @Query(ExerciseConstants.QUERY_EXERCISE_BY_ID + ":id")
    abstract fun getExerciseById(id: Int): Observable<ExerciseDbModel>
}