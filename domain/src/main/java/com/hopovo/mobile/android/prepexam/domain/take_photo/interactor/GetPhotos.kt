package com.hopovo.mobile.android.prepexam.domain.take_photo.interactor

import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.interactor.ObservableUseCase
import com.hopovo.mobile.android.prepexam.domain.take_photo.repository.TakePhotoRepository
import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import io.reactivex.Observable

open class GetPhotos(
        private val takePhotoRepository: TakePhotoRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<GolingPhoto>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Observable<List<GolingPhoto>> {
        return takePhotoRepository.getPhotos()
    }
}