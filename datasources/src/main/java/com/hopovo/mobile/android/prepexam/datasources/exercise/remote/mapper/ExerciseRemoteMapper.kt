package com.hopovo.mobile.android.prepexam.datasources.exercise.remote.mapper

import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.model.ExerciseResponse
import com.hopovo.mobile.android.prepexam.datasources.remote.mapper.EntityMapper
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise

/**
 * Map a [ExerciseResponse] to and from a [Exercise] instance when data is moving between
 * this later and the Data layer
 */
open class ExerciseRemoteMapper : EntityMapper<ExerciseResponse, Exercise> {

    /**
     * Map an instance of a [ExerciseResponse] to a [Exercise] model
     */
    override fun mapFromRemote(type: ExerciseResponse): Exercise {
        return Exercise(type.id, type.description, type.title, type.image, type.level, type.time)
    }
}