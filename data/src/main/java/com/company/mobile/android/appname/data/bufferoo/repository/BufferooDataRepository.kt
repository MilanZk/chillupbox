package com.company.mobile.android.appname.data.bufferoo.repository

import com.company.mobile.android.appname.data.bufferoo.source.BufferooDataStoreFactory
import com.company.mobile.android.appname.domain.bufferoo.repository.BufferooRepository
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.model.bufferoo.SignedInBufferoo
import com.company.mobile.android.appname.model.bufferoo.SignedOutBufferoo
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