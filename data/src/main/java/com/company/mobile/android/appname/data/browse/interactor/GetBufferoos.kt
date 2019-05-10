package com.company.mobile.android.appname.data.browse.interactor

import com.company.mobile.android.appname.data.browse.Bufferoo
import com.company.mobile.android.appname.data.executor.PostExecutionThread
import com.company.mobile.android.appname.data.executor.ThreadExecutor
import com.company.mobile.android.appname.data.interactor.SingleUseCase
import com.company.mobile.android.appname.data.repository.BufferooRepository
import io.reactivex.Single

open class GetBufferoos(
    private val bufferooRepository: BufferooRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Bufferoo>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<List<Bufferoo>> {
        return bufferooRepository.getBufferoos()
    }
}