package com.hopovo.mobile.android.prepexam.datasources.take_photo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GolingPhoto")
data class GolingPhotoDbModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val photoUri: String
)