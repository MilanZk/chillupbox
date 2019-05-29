package com.company.mobile.android.appname.domain.bufferoo.interactor

import com.company.mobile.android.appname.domain.bufferoo.repository.BufferooRepository
import com.company.mobile.android.appname.domain.executor.PostExecutionThread
import com.company.mobile.android.appname.domain.executor.ThreadExecutor
import com.company.mobile.android.appname.domain.interactor.SingleUseCase
import com.company.mobile.android.appname.model.bufferoo.Credentials
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