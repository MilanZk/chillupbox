package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.mapper

import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.ExerciseDbModel
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise

class ExerciseMapper {

    fun mapFromCached(type: ExerciseDbModel): Exercise {
        return Exercise(
                type.id,
                type.word,
                type.description,
                type.image,
                type.translation
        )
    }

    fun mapToCached(type: Exercise): ExerciseDbModel {
        return ExerciseDbModel(
                type.id,
                type.word,
                type.description,
                type.image,
                type.translation
        )
    }
}