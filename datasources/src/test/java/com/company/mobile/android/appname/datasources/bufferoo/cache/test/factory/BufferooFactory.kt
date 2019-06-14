package com.company.mobile.android.appname.datasources.bufferoo.cache.test.factory

import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.datasources.bufferoo.cache.model.CachedBufferoo
import com.company.mobile.android.appname.datasources.bufferoo.cache.test.factory.DataFactory.Factory.randomLong
import com.company.mobile.android.appname.datasources.bufferoo.cache.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeCachedBufferoo(): CachedBufferoo {
            return CachedBufferoo(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeBufferooEntity(): Bufferoo {
            return Bufferoo(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeBufferooEntityList(count: Int): List<Bufferoo> {
            val bufferooEntities = mutableListOf<Bufferoo>()
            repeat(count) {
                bufferooEntities.add(makeBufferooEntity())
            }
            return bufferooEntities
        }

        fun makeCachedBufferooList(count: Int): List<CachedBufferoo> {
            val cachedBufferoos = mutableListOf<CachedBufferoo>()
            repeat(count) {
                cachedBufferoos.add(makeCachedBufferoo())
            }
            return cachedBufferoos
        }
    }
}