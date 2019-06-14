package com.company.mobile.android.appname.datasources.bufferoo.remote.mapper

import com.company.mobile.android.appname.datasources.bufferoo.remote.test.factory.BufferooFactory
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BufferooMapperTest {

    private val bufferooEntityMapper = BufferooEntityMapper()

    @Test
    fun mapFromRemoteMapsData() {
        val bufferooResponse = BufferooFactory.makeBufferooResponse()
        val bufferooEntity = bufferooEntityMapper.mapFromRemote(bufferooResponse)

        assertEquals(bufferooResponse.name, bufferooEntity.name)
        assertEquals(bufferooResponse.title, bufferooEntity.title)
        assertEquals(bufferooResponse.avatar, bufferooEntity.avatar)
    }
}