package com.hopovo.mobile.android.prepexam.app.test.util

import com.hopovo.mobile.android.prepexam.model.exercise.Exercise

/**
 * Factory class for Exercise related instances
 */
object BufferooFactory {

    fun makeBufferooList(count: Int): List<Exercise> {
        val bufferoos = mutableListOf<Exercise>()
        repeat(count) {
            bufferoos.add(makeBufferooModel())
        }
        return bufferoos
    }

    fun makeBufferooModel(): Exercise {
        return Exercise(
            DataFactory.randomLong(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid()
        )
    }
}