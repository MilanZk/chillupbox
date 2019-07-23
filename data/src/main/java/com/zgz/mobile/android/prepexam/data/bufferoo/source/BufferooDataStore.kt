package com.zgz.mobile.android.prepexam.data.bufferoo.source

import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.Credentials
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedInBufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedOutBufferoo
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface BufferooDataStore {

    fun signIn(username: String, password: String): Single<SignedInBufferoo>

    fun getCredentials(): Single<Credentials>

    fun getBufferoos(): Single<List<Bufferoo>>

    fun saveBufferoos(bufferoos: List<Bufferoo>): Completable

    fun clearBufferoos(): Completable

    fun isValidCache(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long)

    fun signOut(): Single<SignedOutBufferoo>
}