package com.hopovo.mobile.android.prepexam.domain.take_photo.interactor

import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.interactor.CompletableUseCase
import com.hopovo.mobile.android.prepexam.domain.take_photo.repository.TakePhotoRepository
import com.hopovo.mobile.android.prepexam.model.take_photo.GolingPhoto
import io.reactivex.Completable

open class SavePhoto(
        private val takePhotoRepository: TakePhotoRepository,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : CompletableUseCase<GolingPhoto>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: GolingPhoto): Completable {
        return takePhotoRepository.savePhoto(params)
    }
}