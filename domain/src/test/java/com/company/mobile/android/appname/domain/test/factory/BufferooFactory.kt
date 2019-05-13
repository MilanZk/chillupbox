package com.company.mobile.android.appname.domain.test.factory

import com.company.mobile.android.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.domain.test.factory.DataFactory.Factory.randomLong
import com.company.mobile.android.appname.domain.test.factory.DataFactory.Factory.randomUuid

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