package com.company.mobile.android.appname.domain.bufferoo.interactor

import com.company.mobile.android.appname.domain.bufferoo.repository.BufferooRepository
import com.company.mobile.android.appname.domain.executor.PostExecutionThread
import com.company.mobile.android.appname.domain.executor.ThreadExecutor
import com.company.mobile.android.appname.domain.interactor.SingleUseCase
import com.company.mobile.android.appname.model.bufferoo.SignedOutBufferoo
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