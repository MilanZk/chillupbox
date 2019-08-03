package com.hopovo.mobile.android.prepexam.data.bufferoo.test.factory

import com.hopovo.mobile.android.prepexam.data.bufferoo.test.factory.DataFactory.Factory.randomLong
import com.hopovo.mobile.android.prepexam.data.bufferoo.test.factory.DataFactory.Factory.randomUuid
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise

/**
 * Factory class for Exercise related instances
 */
object BufferooFactory {

    fun makeBufferoo(): Exercise {
        return Exercise(randomLong(), randomUuid(), randomUuid(), randomUuid())
    }

    fun makeBufferooList(count: Int): List<Exercise> {
        val bufferoos = mutableListOf<Exercise>()
        repeat(count) {
            bufferoos.add(makeBufferoo())
        }
        return bufferoos
    }
}