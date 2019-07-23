package com.zgz.mobile.android.prepexam.domain.bufferoo.interactor

import com.zgz.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.zgz.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.zgz.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.zgz.mobile.android.prepexam.domain.interactor.SingleUseCase
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
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