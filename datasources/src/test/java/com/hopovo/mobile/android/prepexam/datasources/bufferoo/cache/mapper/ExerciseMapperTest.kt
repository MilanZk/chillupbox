package com.hopovo.mobile.android.prepexam.datasources.bufferoo.cache.mapper

import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.model.ExerciseDbModel
import com.hopovo.mobile.android.prepexam.datasources.bufferoo.cache.test.factory.BufferooFactory
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.mapper.BufferooEntityMapper
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ExerciseMapperTest {

    private val bufferooEntityMapper = BufferooEntityMapper()

    @Test
    fun mapToCachedMapsData() {
        val bufferooEntity = BufferooFactory.makeBufferooEntity()
        val cachedBufferoo = bufferooEntityMapper.mapToCached(bufferooEntity)

        assertBufferooDataEquality(bufferooEntity, cachedBufferoo)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedBufferoo = BufferooFactory.makeCachedBufferoo()
        val bufferooEntity = bufferooEntityMapper.mapFromCached(cachedBufferoo)

        assertBufferooDataEquality(bufferooEntity, cachedBufferoo)
    }

    private fun assertBufferooDataEquality(
            exercise: Exercise,
            exerciseDbModel: ExerciseDbModel
    ) {
        assertEquals(exercise.description, exerciseDbModel.description)
        assertEquals(exercise.word, exerciseDbModel.word)
        assertEquals(exercise.image, exerciseDbModel.image)
    }
}