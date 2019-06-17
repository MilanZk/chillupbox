package com.company.mobile.android.appname.data.bufferoo.test.factory

import com.company.mobile.android.appname.data.bufferoo.test.factory.DataFactory.Factory.randomLong
import com.company.mobile.android.appname.data.bufferoo.test.factory.DataFactory.Factory.randomUuid
import com.company.mobile.android.appname.model.bufferoo.Bufferoo

/**
 * Factory class for Bufferoo related instances
 */
object BufferooFactory {

    fun makeBufferoo(): Bufferoo {
        return Bufferoo(randomLong(), randomUuid(), randomUuid(), randomUuid())
    }

    fun makeBufferooList(count: Int): List<Bufferoo> {
        val bufferoos = mutableListOf<Bufferoo>()
        repeat(count) {
            bufferoos.add(makeBufferoo())
        }
        return bufferoos
    }
}