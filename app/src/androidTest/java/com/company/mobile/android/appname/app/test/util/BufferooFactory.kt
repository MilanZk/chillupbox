package com.company.mobile.android.appname.app.test.util

import com.company.mobile.android.appname.model.bufferoo.Bufferoo

/**
 * Factory class for Bufferoo related instances
 */
object BufferooFactory {

    fun makeBufferooList(count: Int): List<Bufferoo> {
        val bufferoos = mutableListOf<Bufferoo>()
        repeat(count) {
            bufferoos.add(makeBufferooModel())
        }
        return bufferoos
    }

    fun makeBufferooModel(): Bufferoo {
        return Bufferoo(
            DataFactory.randomLong(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid()
        )
    }
}