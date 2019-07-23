package com.zgz.mobile.android.prepexam.domain.bufferoo.interactor

import com.zgz.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.zgz.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.zgz.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.zgz.mobile.android.prepexam.domain.interactor.SingleUseCase
import com.zgz.mobile.android.prepexam.model.bufferoo.Credentials
import io.reactivex.Single

open class GetCredentials(
    private val bufferooRepository: BufferooRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Credentials, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<Credentials> {
        return bufferooRepository.getCredentials()
    }
}