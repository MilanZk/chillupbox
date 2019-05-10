package com.company.mobile.android.appname.datasources.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.company.mobile.android.appname.datasources.cache.dao.CachedBufferooDao
import com.company.mobile.android.appname.datasources.cache.model.CachedBufferoo

@Database(entities = arrayOf(CachedBufferoo::class), version = 1)
abstract class BufferoosDatabase : RoomDatabase() {

    private var INSTANCE: BufferoosDatabase? = null
    private val sLock = Any()

    abstract fun cachedBufferooDao(): CachedBufferooDao

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