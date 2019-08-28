package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.constants.ExerciseConstants

/**
 * Model used solely for the caching of a bufferroo
 */
@Entity(tableName = ExerciseConstants.TABLE_NAME)
data class ExerciseDbModel(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        val description: String,
        val word: String,
        val image: String,
        val translation: String)