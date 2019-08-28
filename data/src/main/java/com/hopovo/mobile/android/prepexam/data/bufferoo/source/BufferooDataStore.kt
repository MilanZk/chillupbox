package com.hopovo.mobile.android.prepexam.data.bufferoo.source

import com.hopovo.mobile.android.prepexam.model.exercise.Credentials
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import com.hopovo.mobile.android.prepexam.model.exercise.SignedInBufferoo
import com.hopovo.mobile.android.prepexam.model.exercise.SignedOutBufferoo
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

    fun getBufferoos(): Single<List<Exercise>>

    fun saveBufferoos(exercises: List<Exercise>): Completable

    fun saveExercise(exercise: Exercise): Completable

    fun clearBufferoos(): Completable

    fun isValidCache(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long)

    fun signOut(): Single<SignedOutBufferoo>
}