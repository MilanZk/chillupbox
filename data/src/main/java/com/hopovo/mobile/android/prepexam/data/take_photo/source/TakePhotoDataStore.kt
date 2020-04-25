package com.hopovo.mobile.android.prepexam.data.take_photo.source

import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import io.reactivex.Completable
import io.reactivex.Observable

interface TakePhotoDataStore {

    fun savePhoto(golingPhoto: GolingPhoto): Completable

    fun getPhotos(): Observable<List<GolingPhoto>>
}
