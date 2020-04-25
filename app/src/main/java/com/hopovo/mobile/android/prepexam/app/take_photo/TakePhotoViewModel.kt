package com.hopovo.mobile.android.prepexam.app.take_photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.SingleLiveEvent
import com.hopovo.mobile.android.prepexam.app.exercise.ExerciseCommand
import com.hopovo.mobile.android.prepexam.domain.take_photo.interactor.GetPhotos
import com.hopovo.mobile.android.prepexam.domain.take_photo.interactor.SavePhoto
import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import io.reactivex.disposables.Disposable
import timber.log.Timber

class TakePhotoViewModel(
        private val savePhoto: SavePhoto,
        private val getPhotos: GetPhotos
) : ViewModel() {

    private var photoMutableData: MutableLiveData<List<GolingPhoto>> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun savePhoto(stringUri: String) {
        disposable = savePhoto.execute(GolingPhoto(photoUri = stringUri)).subscribe({
            Timber.d("Image saved successfully")
        }, {
            Timber.e(it)
        })
    }

    fun getPhotos(): LiveData<List<GolingPhoto>> {
        return photoMutableData
    }

    fun fetchPhotos(){
        disposable = getPhotos.execute().subscribe({
            photoMutableData.value = it
        }, {
            Timber.e(it)
        })

    }
}