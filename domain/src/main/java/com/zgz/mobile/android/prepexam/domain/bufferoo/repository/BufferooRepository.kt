package com.zgz.mobile.android.prepexam.domain.bufferoo.repository

import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.Credentials
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedInBufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedOutBufferoo
import io.reactivex.Completable
import io.reactivex.Single

interface BufferooRepository {

    fun signIn(username: String, password: String): Single<SignedInBufferoo>

    fun getCredentials(): Single<Credentials>

    fun getBufferoos(): Single<List<Bufferoo>>

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun clearBufferoos(): Completable

    fun signOut(): Single<SignedOutBufferoo>
}