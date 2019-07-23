package com.zgz.mobile.android.prepexam.domain.bufferoo.interactor

import com.zgz.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.zgz.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.zgz.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.zgz.mobile.android.prepexam.domain.interactor.SingleUseCase
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedOutBufferoo
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