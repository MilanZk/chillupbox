package org.buffer.android.boilerplate.data.browse.interactor

import io.reactivex.Single
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.data.executor.PostExecutionThread
import org.buffer.android.boilerplate.data.executor.ThreadExecutor
import org.buffer.android.boilerplate.data.interactor.SingleUseCase
import org.buffer.android.boilerplate.data.repository.BufferooRepository

class GetBufferoos(
    private val bufferooRepository: BufferooRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Bufferoo>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<List<Bufferoo>> {
        return bufferooRepository.getBufferoos()
    }
}