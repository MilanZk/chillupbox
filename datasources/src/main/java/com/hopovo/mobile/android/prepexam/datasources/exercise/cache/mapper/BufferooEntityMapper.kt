package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.mapper

import com.hopovo.mobile.android.prepexam.datasources.cache.mapper.EntityMapper
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.model.ExerciseDbModel
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise

/**
 * Map a [ExerciseDbModel] instance to and from a [Exercise] instance when data is moving between
 * this later and the Data layer
 */
open class BufferooEntityMapper : EntityMapper<ExerciseDbModel, Exercise> {

    /**
     * Map a [Exercise] instance to a [ExerciseDbModel] instance
     */
    override fun mapToCached(type: Exercise): ExerciseDbModel {
        return ExerciseDbModel(type.id, type.description, type.title, type.image, type.level, type.time)
    }

    /**
     * Map a [ExerciseDbModel] instance to a [Exercise] instance
     */
    override fun mapFromCached(type: ExerciseDbModel): Exercise {
        return Exercise(type.id, type.description, type.title, type.image, type.level, type.time)
    }
}