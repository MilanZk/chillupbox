package org.buffer.android.boilerplate.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.browse.Bufferoo

interface BufferooRepository {

    fun clearBufferoos(): Completable

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun getBufferoos(): Single<List<Bufferoo>>
}