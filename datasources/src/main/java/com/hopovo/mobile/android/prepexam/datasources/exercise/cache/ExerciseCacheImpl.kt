package com.hopovo.mobile.android.prepexam.datasources.exercise.cache

import com.hopovo.mobile.android.prepexam.data.bufferoo.source.BufferooDataStore
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.BufferoosDatabase
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.mapper.BufferooEntityMapper
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.model.ExerciseDbModel
import com.hopovo.mobile.android.prepexam.model.exercise.Credentials
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import com.hopovo.mobile.android.prepexam.model.exercise.SignedInBufferoo
import com.hopovo.mobile.android.prepexam.model.exercise.SignedOutBufferoo
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Cached implementation for retrieving and saving Exercise instances. This class implements the
 * [BufferooCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class ExerciseCacheImpl constructor(
        val bufferoosDatabase: BufferoosDatabase,
        private val entityMapper: BufferooEntityMapper,
        private val preferencesHelper: PreferencesHelper
) : BufferooDataStore {
    override fun saveExercise(exercise: Exercise): Completable {
        return Completable.defer {
            bufferoosDatabase.cachedBufferooDao().insertExercise(
                    entityMapper.mapToCached(exercise)
            )
            Completable.complete()
        }
    }

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
     * Retrieve a list of [Exercise] instances from the database.
     */
    override fun getBufferoos(): Single<List<Exercise>> {
        return Single.defer {
            Single.just(bufferoosDatabase.cachedBufferooDao().getExercises())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Save the given list of [Exercise] instances to the database.
     */
    override fun saveBufferoos(exercises: List<Exercise>): Completable {
        return Completable.defer {
            exercises.forEach {
                bufferoosDatabase.cachedBufferooDao().insertExercise(
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
            bufferoosDatabase.cachedBufferooDao().clearExercise()
            Completable.complete()
        }
    }

    /**
     * Check whether there are instances of [ExerciseDbModel] stored in the cache.
     */
    override fun isValidCache(): Single<Boolean> {
        return Single.defer {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = getLastCacheUpdateTimeMillis()
            val expired = currentTime - lastUpdateTime > EXPIRATION_TIME
            Single.just(bufferoosDatabase.cachedBufferooDao().getExercises().isNotEmpty() && !expired)
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

    override fun signIn(username: String, password: String): Single<SignedInBufferoo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCredentials(): Single<Credentials> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signOut(): Single<SignedOutBufferoo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}