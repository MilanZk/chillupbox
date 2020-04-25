package com.hopovo.mobile.android.prepexam.datasources.exercise.cache


import com.hopovo.mobile.android.prepexam.data.exercise.source.ExerciseDataStore
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.ExerciseDatabase
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.mapper.ExerciseMapper
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class ExerciseCacheImpl constructor(
        val exerciseDatabase: ExerciseDatabase,
        private val exerciseMapper: ExerciseMapper,
        private val preferencesHelper: PreferencesHelper
) : ExerciseDataStore {
    override fun getExerciseById(idExercise: Int): Observable<Exercise> {
        return Observable.defer {
            exerciseDatabase.exerciseDao().getExerciseById(idExercise).map {
                exerciseMapper.mapFromCached(it)
            }
        }
    }

    override fun getExercise(): Observable<List<Exercise>> {
        return Observable.defer {
            exerciseDatabase.exerciseDao().getExercises().map {
                it.map {
                    exerciseMapper.mapFromCached(it)
                }
            }
        }
    }

    override fun isValidCache(): Single<Boolean> {
        TODO("Not yet implemented")
    }

    override fun saveExercise(exercise: Exercise): Completable {
        return Completable.defer {
            exerciseDatabase.exerciseDao().insertExercise(
                    exerciseMapper.mapToCached(exercise)
            )
        }
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}