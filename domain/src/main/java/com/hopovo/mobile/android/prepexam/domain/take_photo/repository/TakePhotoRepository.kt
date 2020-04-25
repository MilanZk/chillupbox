package com.hopovo.mobile.android.prepexam.domain.take_photo.repository

import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import io.reactivex.Completable
import io.reactivex.Observable

interface TakePhotoRepository{

    fun savePhoto(photoUri: GolingPhoto): Completable

    fun getPhotos(): Observable<List<GolingPhoto>>
}