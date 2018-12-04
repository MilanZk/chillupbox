package org.buffer.android.boilerplate.cache

import io.reactivex.Completable
import io.reactivex.Single
import org.buffer.android.boilerplate.cache.db.BufferoosDatabase
import org.buffer.android.boilerplate.cache.mapper.BufferooEntityMapper
import org.buffer.android.boilerplate.cache.model.CachedBufferoo
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.source.BufferooDataStore

/**
 * Cached implementation for retrieving and saving Bufferoo instances. This class implements the
 * [BufferooCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class BufferooCacheImpl constructor(
    val bufferoosDatabase: BufferoosDatabase,
    private val entityMapper: BufferooEntityMapper,
    private val preferencesHelper: PreferencesHelper
) : BufferooDataStore {

    companion object {
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): BufferoosDatabase {
        return bufferoosDatabase
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearBufferoos(): Completable {
        return Completable.defer {
            bufferoosDatabase.cachedBufferooDao().clearBufferoos()
            Completable.complete()
        }
    }

    /**
     * Save the given list of [Bufferoo] instances to the database.
     */
    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        return Completable.defer {
            bufferoos.forEach {
                bufferoosDatabase.cachedBufferooDao().insertBufferoo(
                    entityMapper.mapToCached(it)
                )
            }
            this.setLastCacheTime(System.currentTimeMillis())
            Completable.complete()
        }
    }

    /**
     * Retrieve a list of [Bufferoo] instances from the database.
     */
    override fun getBufferoos(): Single<List<Bufferoo>> {
        return Single.defer {
            Single.just(bufferoosDatabase.cachedBufferooDao().getBufferoos())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Check whether there are instances of [CachedBufferoo] stored in the cache.
     */
    override fun isValidCache(): Single<Boolean> {
        return Single.defer {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
            val expired = currentTime - lastUpdateTime > EXPIRATION_TIME
            Single.just(bufferoosDatabase.cachedBufferooDao().getBufferoos().isNotEmpty() && !expired)
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }
}