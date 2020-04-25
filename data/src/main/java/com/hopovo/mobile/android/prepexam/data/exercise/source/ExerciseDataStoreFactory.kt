package com.hopovo.mobile.android.prepexam.data.exercise.source

open class ExerciseDataStoreFactory(
        private val exerciseCacheDataStore: ExerciseDataStore
) {

    open fun retrieveCacheDataStore(): ExerciseDataStore {
        return exerciseCacheDataStore
    }

}