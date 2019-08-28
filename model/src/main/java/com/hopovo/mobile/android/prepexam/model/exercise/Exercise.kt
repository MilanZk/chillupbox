package com.hopovo.mobile.android.prepexam.model.exercise

/**
 * Representation for a [Exercise] fetched from an external layer data source
 */
data class Exercise(val id: Long,
                    val word: String,
                    val description: String,
                    val image: String,
                    val translation: String
) {
    constructor(title: String, description: String, image: String, translation: String)
            : this(0, title, description, image, translation)
}
