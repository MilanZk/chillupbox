package com.company.mobile.android.appname.domain.bufferoo.interactor

import com.company.mobile.android.appname.domain.bufferoo.interactor.SignInBufferoos.SignInParams
import com.company.mobile.android.appname.domain.bufferoo.repository.BufferooRepository
import com.company.mobile.android.appname.domain.executor.PostExecutionThread
import com.company.mobile.android.appname.domain.executor.ThreadExecutor
import com.company.mobile.android.appname.domain.interactor.SingleUseCase
import com.company.mobile.android.appname.model.bufferoo.SignedInBufferoo
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