package com.hopovo.mobile.android.prepexam.data.bufferoo.repository

import com.hopovo.mobile.android.prepexam.data.bufferoo.source.BufferooDataStoreFactory
import com.hopovo.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.hopovo.mobile.android.prepexam.model.exercise.Credentials
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import com.hopovo.mobile.android.prepexam.model.exercise.SignedInBufferoo
import com.hopovo.mobile.android.prepexam.model.exercise.SignedOutBufferoo
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Provides an implementation of the [BufferooRepository] interface for communicating to and from
 * data sources
 */
class ExerciseDataRepository(private val factory: BufferooDataStoreFactory) : BufferooRepository {

    override fun saveExercise(exercise: Exercise): Completable {
        return factory.retrieveCacheDataStore().saveExercise(exercise)
    }

    override fun signIn(username: String, password: String): Single<SignedInBufferoo> {
        return factory.retrieveRemoteDataStore().signIn(username, password)
    }

    override fun getCredentials(): Single<Credentials> {
        return factory.retrieveRemoteDataStore().getCredentials()
    }

    override fun getBufferoos(): Single<List<Exercise>> {
        return factory.retrieveCacheDataStore().getBufferoos()
    }

    override fun saveBufferoos(exercises: List<Exercise>): Completable {
        return factory.retrieveCacheDataStore().saveBufferoos(exercises)
    }

    override fun clearBufferoos(): Completable {
        return factory.retrieveCacheDataStore().clearBufferoos()
    }

    override fun signOut(): Single<SignedOutBufferoo> {
        return factory.retrieveRemoteDataStore().signOut()
    }
}