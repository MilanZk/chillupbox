package com.hopovo.mobile.android.prepexam.datasources.bufferoo.remote.test.factory

import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.BufferooService
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.model.ExerciseResponse
import com.hopovo.mobile.android.prepexam.datasources.bufferoo.remote.test.factory.DataFactory.Factory.randomLong
import com.hopovo.mobile.android.prepexam.datasources.bufferoo.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Exercise related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferoosResponse(): BufferooService.BufferoosResponse {
            val bufferoosResponse = BufferooService.BufferoosResponse()
            bufferoosResponse.items = makeBufferooResponseList(5)
            return bufferoosResponse
        }

        fun makeBufferooResponseList(count: Int): List<ExerciseResponse> {
            val bufferooEntities = mutableListOf<ExerciseResponse>()
            repeat(count) {
                bufferooEntities.add(makeBufferooResponse())
            }
            return bufferooEntities
        }

        fun makeBufferooResponse(): ExerciseResponse {
            return ExerciseResponse(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }
    }
}