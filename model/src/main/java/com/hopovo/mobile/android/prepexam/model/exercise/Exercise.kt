package com.hopovo.mobile.android.prepexam.model.exercise

/**
 * Representation for a [Exercise] fetched from an external layer data source
 */
data class Exercise(val id: Long,
                    val description: String,
                    val title: String,
                    val image: String,
                    val level: String,
                    val time: String)