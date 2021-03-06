package com.hopovo.mobile.android.prepexam.domain.bufferoo.repository

import com.hopovo.mobile.android.prepexam.model.exercise.Credentials
import com.hopovo.mobile.android.prepexam.model.exercise.SignedInBufferoo
import com.hopovo.mobile.android.prepexam.model.exercise.SignedOutBufferoo
import io.reactivex.Single

interface BufferooRepository {

    fun signIn(username: String, password: String): Single<SignedInBufferoo>

    fun getCredentials(): Single<Credentials>

    fun signOut(): Single<SignedOutBufferoo>
}