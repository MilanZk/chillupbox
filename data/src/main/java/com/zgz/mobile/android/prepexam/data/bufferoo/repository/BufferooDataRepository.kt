package com.zgz.mobile.android.prepexam.data.bufferoo.repository

import com.zgz.mobile.android.prepexam.data.bufferoo.source.BufferooDataStoreFactory
import com.zgz.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.Credentials
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedInBufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedOutBufferoo
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Provides an implementation of the [BufferooRepository] interface for communicating to and from
 * data sources
 */
class BufferooDataRepository(private val factory: BufferooDataStoreFactory) : BufferooRepository {

    override fun signIn(username: String, password: String): Single<SignedInBufferoo> {
        return factory.retrieveRemoteDataStore().signIn(username, password)
    }

    override fun getCredentials(): Single<Credentials> {
        return factory.retrieveRemoteDataStore().getCredentials()
    }

    override fun getBufferoos(): Single<List<Bufferoo>> {
        return factory.retrieveCacheDataStore().isValidCache()
            .flatMap { cached ->
                // Get data store based on whether cached data is valid
                val bufferooDataStore = factory.retrieveDataStore(cached)

                val bufferooListSource = if (cached) {
                    // Getting data from cache
                    bufferooDataStore.getBufferoos()
                } else {
                    // Getting data from remote, so result is cached
                    bufferooDataStore.getBufferoos()
                        .flatMap { bufferooList ->
                            // Once the result have been retrieved, save it to cache and return it
                            saveBufferoos(bufferooList).toSingle { bufferooList }
                        }
                }

                bufferooListSource
            }
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        return factory.retrieveCacheDataStore().saveBufferoos(bufferoos)
    }

    override fun clearBufferoos(): Completable {
        return factory.retrieveCacheDataStore().clearBufferoos()
    }

    override fun signOut(): Single<SignedOutBufferoo> {
        return factory.retrieveRemoteDataStore().signOut()
    }
}