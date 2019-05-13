package com.company.mobile.android.appname.domain.bufferoo.repository

import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import io.reactivex.Completable
import io.reactivex.Single

interface BufferooRepository {

    fun clearBufferoos(): Completable

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun getBufferoos(): Single<List<Bufferoo>>
}