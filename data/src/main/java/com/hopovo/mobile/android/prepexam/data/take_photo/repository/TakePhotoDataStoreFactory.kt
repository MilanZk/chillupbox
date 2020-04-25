package com.hopovo.mobile.android.prepexam.data.take_photo.repository

import com.hopovo.mobile.android.prepexam.data.take_photo.source.TakePhotoDataStore

open class TakePhotoDataStoreFactory(
        private val takePhotoDataStore: TakePhotoDataStore
) {

    open fun retrieveTakePhotoCacheDataStore(): TakePhotoDataStore {
        return takePhotoDataStore
    }

}