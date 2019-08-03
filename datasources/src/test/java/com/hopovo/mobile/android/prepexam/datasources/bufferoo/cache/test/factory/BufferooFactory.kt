package com.hopovo.mobile.android.prepexam.datasources.bufferoo.cache.test.factory

import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.model.ExerciseDbModel
import com.hopovo.mobile.android.prepexam.datasources.bufferoo.cache.test.factory.DataFactory.Factory.randomLong
import com.hopovo.mobile.android.prepexam.datasources.bufferoo.cache.test.factory.DataFactory.Factory.randomUuid
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise

/**
 * Factory class for Exercise related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeCachedBufferoo(): ExerciseDbModel {
            return ExerciseDbModel(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeBufferooEntity(): Exercise {
            return Exercise(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeBufferooEntityList(count: Int): List<Exercise> {
            val bufferooEntities = mutableListOf<Exercise>()
            repeat(count) {
                bufferooEntities.add(makeBufferooEntity())
            }
            return bufferooEntities
        }

        fun makeCachedBufferooList(count: Int): List<ExerciseDbModel> {
            val cachedBufferoos = mutableListOf<ExerciseDbModel>()
            repeat(count) {
                cachedBufferoos.add(makeCachedBufferoo())
            }
            return cachedBufferoos
        }
    }
}