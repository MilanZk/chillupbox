package com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.db.constants.BufferooConstants
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.model.CachedBufferoo

@Dao
abstract class CachedBufferooDao {

    @Query(BufferooConstants.QUERY_BUFFEROOS)
    abstract fun getBufferoos(): List<CachedBufferoo>

    @Query(BufferooConstants.DELETE_ALL_BUFFEROOS)
    abstract fun clearBufferoos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBufferoo(cachedBufferoo: CachedBufferoo)
}