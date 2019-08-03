package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.constants

/**
 * Defines constants for the Bufferoos Table
 */
object ExerciseConstants {

    const val TABLE_NAME = "exercise"
    const val QUERY_EXERCISE = "SELECT * FROM $TABLE_NAME"
    const val DELETE_ALL_EXERCISE = "DELETE FROM $TABLE_NAME"
}