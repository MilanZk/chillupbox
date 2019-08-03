package com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.dao.ExerciseDao
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.model.ExerciseDbModel

@Database(entities = arrayOf(ExerciseDbModel::class), version = 1)
abstract class BufferoosDatabase : RoomDatabase() {

    private var INSTANCE: BufferoosDatabase? = null
    private val sLock = Any()

    abstract fun cachedBufferooDao(): ExerciseDao

    fun getInstance(context: Context): BufferoosDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BufferoosDatabase::class.java, "bufferoos.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }
}