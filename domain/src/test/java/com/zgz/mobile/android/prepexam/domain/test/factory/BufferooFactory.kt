package com.zgz.mobile.android.prepexam.domain.test.factory

import com.zgz.mobile.android.prepexam.domain.test.factory.DataFactory.Factory.randomLong
import com.zgz.mobile.android.prepexam.domain.test.factory.DataFactory.Factory.randomUuid
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo

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