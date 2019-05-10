package org.buffer.android.boilerplate.datasources.remote.mapper

import org.buffer.android.boilerplate.datasources.remote.test.factory.BufferooFactory
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BufferooMapperTest {

    private val bufferooEntityMapper = BufferooEntityMapper()

    @Test
    fun mapFromRemoteMapsData() {
        val bufferooModel = BufferooFactory.makeBufferooModel()
        val bufferooEntity = bufferooEntityMapper.mapFromRemote(bufferooModel)

        assertEquals(bufferooModel.name, bufferooEntity.name)
        assertEquals(bufferooModel.title, bufferooEntity.title)
        assertEquals(bufferooModel.avatar, bufferooEntity.avatar)
    }
}