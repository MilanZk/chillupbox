package com.zgz.mobile.android.prepexam.app.test.util

import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo

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