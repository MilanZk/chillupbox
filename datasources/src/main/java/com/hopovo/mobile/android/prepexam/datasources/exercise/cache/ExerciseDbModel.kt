package com.hopovo.mobile.android.prepexam.datasources.exercise.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Exercise")
data class ExerciseDbModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val word: String,
        val description: String,
        val image: String,
        val translation: String
)