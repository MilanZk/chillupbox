package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.ExerciseDbModel
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.dao.ExerciseDao
import com.hopovo.mobile.android.prepexam.datasources.take_photo.GolingPhotoDbModel
import com.hopovo.mobile.android.prepexam.datasources.take_photo.cache.TakePhotoDao

@Database(entities = arrayOf(ExerciseDbModel::class, GolingPhotoDbModel::class), version = 1)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun takePhotoDao(): TakePhotoDao
}