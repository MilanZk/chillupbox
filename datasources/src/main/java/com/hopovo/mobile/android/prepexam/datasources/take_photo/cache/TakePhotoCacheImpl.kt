package com.hopovo.mobile.android.prepexam.datasources.take_photo.cache

import com.hopovo.mobile.android.prepexam.data.take_photo.source.TakePhotoDataStore
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.db.ExerciseDatabase
import com.hopovo.mobile.android.prepexam.datasources.exercise.cache.mapper.ExerciseMapper
import com.hopovo.mobile.android.prepexam.datasources.take_photo.TakePhotoMapper
import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import io.reactivex.Completable
import io.reactivex.Observable

class TakePhotoCacheImpl constructor(
        val exerciseDatabase: ExerciseDatabase,
        val golingMapper: TakePhotoMapper
) : TakePhotoDataStore {

    override fun savePhoto(golingPhoto: GolingPhoto): Completable {
        return exerciseDatabase.takePhotoDao().insertGoLingPhoto(golingMapper.mapToCached(golingPhoto))
    }

    override fun getPhotos(): Observable<List<GolingPhoto>> {
        return exerciseDatabase.takePhotoDao().getAllPhotos().map { photos ->
            photos.map {
                golingMapper.mapFromCached(it)
            }
        }
    }
}