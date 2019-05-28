package com.company.mobile.android.appname.datasources.bufferoo.cache

import com.company.mobile.android.appname.data.bufferoo.source.BufferooDataStore
import com.company.mobile.android.appname.datasources.bufferoo.cache.db.BufferoosDatabase
import com.company.mobile.android.appname.datasources.bufferoo.cache.mapper.BufferooEntityMapper
import com.company.mobile.android.appname.datasources.bufferoo.cache.model.CachedBufferoo
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.model.bufferoo.SignedInBufferoo
import io.reactivex.Completable
import io.reactivex.Single

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

    override fun signIn(username: String, password: String): Single<SignedInBufferoo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
     * Save the given list of [Bufferoo] instances to the database.
     */
    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        return Completable.defer {
            bufferoos.forEach {
                bufferoosDatabase.cachedBufferooDao().insertBufferoo(
                    entityMapper.mapToCached(it)
                )
            }
            setLastCacheTime(System.currentTimeMillis())
            Completable.complete()
        }
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
     * Check whether there are instances of [CachedBufferoo] stored in the cache.
     */
    override fun isValidCache(): Single<Boolean> {
        return Single.defer {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = getLastCacheUpdateTimeMillis()
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