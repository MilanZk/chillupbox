package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.constants.ExerciseConstants
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.model.ExerciseDbModel

@Dao
abstract class ExerciseDao {

    @Query(ExerciseConstants.QUERY_EXERCISE)
    abstract fun getExercises(): List<ExerciseDbModel>

    @Query(ExerciseConstants.DELETE_ALL_EXERCISE)
    abstract fun clearExercise()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertExercise(exerciseDbModel: ExerciseDbModel)
}