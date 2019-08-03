package com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor

import com.hopovo.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.hopovo.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.hopovo.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.hopovo.mobile.android.prepexam.domain.interactor.SingleUseCase
import com.hopovo.mobile.android.prepexam.model.exercise.SignedOutBufferoo
import io.reactivex.Single

open class SignOutBufferoos(
    private val bufferooRepository: BufferooRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<SignedOutBufferoo, Void?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Single<SignedOutBufferoo> {
        return bufferooRepository.signOut()
    }
}