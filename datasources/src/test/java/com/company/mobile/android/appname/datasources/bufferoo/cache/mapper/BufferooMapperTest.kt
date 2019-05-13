package com.company.mobile.android.appname.datasources.bufferoo.cache.mapper

import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.datasources.bufferoo.cache.model.CachedBufferoo
import com.company.mobile.android.appname.datasources.bufferoo.cache.test.factory.BufferooFactory
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BufferooMapperTest {

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
        bufferoo: Bufferoo,
        cachedBufferoo: CachedBufferoo
    ) {
        assertEquals(bufferoo.name, cachedBufferoo.name)
        assertEquals(bufferoo.title, cachedBufferoo.title)
        assertEquals(bufferoo.avatar, cachedBufferoo.avatar)
    }
}