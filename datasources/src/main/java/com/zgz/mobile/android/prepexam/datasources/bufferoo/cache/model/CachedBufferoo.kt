package com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zgz.mobile.android.prepexam.datasources.bufferoo.cache.db.constants.BufferooConstants

/**
 * Model used solely for the caching of a bufferroo
 */
@Entity(tableName = BufferooConstants.TABLE_NAME)
data class CachedBufferoo(
    @PrimaryKey
    var id: Long,
    val name: String,
    val title: String,
    val avatar: String
)