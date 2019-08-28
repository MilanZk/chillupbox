package com.hopovo.mobile.android.prepexam.datasources.bufferoo.remote.mapper

import com.hopovo.mobile.android.prepexam.datasources.bufferoo.remote.test.factory.BufferooFactory
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.mapper.ExerciseRemoteMapper
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ExerciseMapperTest {

    private val bufferooEntityMapper = ExerciseRemoteMapper()

    @Test
    fun mapFromRemoteMapsData() {
        val bufferooResponse = BufferooFactory.makeBufferooResponse()
        val bufferooEntity = bufferooEntityMapper.mapFromRemote(bufferooResponse)

        assertEquals(bufferooResponse.description, bufferooEntity.description)
        assertEquals(bufferooResponse.title, bufferooEntity.word)
        assertEquals(bufferooResponse.image, bufferooEntity.image)
    }
}