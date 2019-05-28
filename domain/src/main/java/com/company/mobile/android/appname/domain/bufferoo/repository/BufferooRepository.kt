package com.company.mobile.android.appname.domain.bufferoo.repository

import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.model.bufferoo.SignedInBufferoo
import io.reactivex.Completable
import io.reactivex.Single

interface BufferooRepository {

    fun signIn(username: String, password: String): Single<SignedInBufferoo>

    fun getBufferoos(): Single<List<Bufferoo>>

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun clearBufferoos(): Completable
}