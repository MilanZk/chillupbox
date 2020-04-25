package com.hopovo.mobile.android.prepexam.datasources.take_photo

import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto

class TakePhotoMapper {

    fun mapFromCached(type: GolingPhotoDbModel): GolingPhoto {
        return GolingPhoto(
                type.id,
                type.photoUri
        )
    }

    fun mapToCached(type: GolingPhoto): GolingPhotoDbModel {
        return GolingPhotoDbModel(
                type.id,
                type.photoUri)
    }
}