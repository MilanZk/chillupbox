package com.zgz.mobile.android.prepexam.domain.bufferoo.interactor

import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.SignInBufferoos.SignInParams
import com.zgz.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.zgz.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.zgz.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.zgz.mobile.android.prepexam.domain.interactor.SingleUseCase
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedInBufferoo
import io.reactivex.Single

open class SignInBufferoos(
    private val bufferooRepository: BufferooRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<SignedInBufferoo, SignInParams?>(threadExecutor, postExecutionThread) {

    data class SignInParams(val username: String, val password: String)

    override fun buildUseCaseObservable(params: SignInParams?): Single<SignedInBufferoo> {
        checkNotNull(params)
        return bufferooRepository.signIn(params.username, params.password)
    }
}