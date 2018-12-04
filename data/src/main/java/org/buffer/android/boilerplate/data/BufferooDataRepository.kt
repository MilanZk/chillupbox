package org.buffer.android.boilerplate.data

import io.reactivex.Completable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.repository.BufferooRepository
import org.buffer.android.boilerplate.data.source.BufferooDataStoreFactory

/**
 * Provides an implementation of the [BufferooRepository] interface for communicating to and from
 * data sources
 */
class BufferooDataRepository(private val factory: BufferooDataStoreFactory) : BufferooRepository {

    override fun clearBufferoos(): Completable {
        return factory.retrieveCacheDataStore().clearBufferoos()
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        return factory.retrieveCacheDataStore().saveBufferoos(bufferoos)
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
}