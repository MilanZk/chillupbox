package com.hopovo.mobile.android.prepexam.datasources.exercise.remote.model

/**
 * Representation for a [ExerciseResponse] fetched from the API
 */
data class ExerciseResponse(val id: Int, val description: String, val title: String,
                            val image: String, val level: String, val time: String)