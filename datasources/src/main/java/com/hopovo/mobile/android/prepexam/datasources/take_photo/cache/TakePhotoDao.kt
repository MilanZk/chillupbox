package com.hopovo.mobile.android.prepexam.datasources.take_photo.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hopovo.mobile.android.prepexam.datasources.take_photo.GolingPhotoDbModel
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class TakePhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertGoLingPhoto(golingPhoto: GolingPhotoDbModel): Completable

    @Query("Select * from GolingPhoto")
    abstract fun getAllPhotos(): Observable<List<GolingPhotoDbModel>>
}