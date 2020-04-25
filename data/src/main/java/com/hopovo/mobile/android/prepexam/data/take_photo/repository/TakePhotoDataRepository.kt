package com.hopovo.mobile.android.prepexam.data.take_photo.repository

import com.hopovo.mobile.android.prepexam.domain.take_photo.repository.TakePhotoRepository
import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import io.reactivex.Completable
import io.reactivex.Observable

class TakePhotoDataRepository(private val factory: TakePhotoDataStoreFactory) : TakePhotoRepository {

    override fun savePhoto(photoUri: GolingPhoto): Completable {
        return factory.retrieveTakePhotoCacheDataStore().savePhoto(photoUri)
    }

    override fun getPhotos(): Observable<List<GolingPhoto>> {
        return factory.retrieveTakePhotoCacheDataStore().getPhotos()
    }
}