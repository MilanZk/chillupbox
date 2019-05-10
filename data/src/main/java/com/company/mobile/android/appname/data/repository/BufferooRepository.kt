package com.company.mobile.android.appname.data.repository

import com.company.mobile.android.appname.data.browse.Bufferoo
import io.reactivex.Completable
import io.reactivex.Single

interface BufferooRepository {

    fun clearBufferoos(): Completable

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun getBufferoos(): Single<List<Bufferoo>>
}