package com.zgz.mobile.android.prepexam.app.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zgz.mobile.android.prepexam.app.common.errorhandling.AppAction.SIGN_IN
import com.zgz.mobile.android.prepexam.app.common.errorhandling.ErrorBundleBuilder
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Error
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Loading
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Success
import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.SignInBufferoos
import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.SignInBufferoos.SignInParams
import io.reactivex.disposables.Disposable

typealias SignInState = ResourceState<String>

class SignInViewModel(private val signInBufferoosUseCase: SignInBufferoos, private val errorBundleBuilder: ErrorBundleBuilder) : ViewModel() {

    private val signInLiveData: MutableLiveData<SignInState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun getSignIn(): LiveData<SignInState> {
        return signInLiveData
    }

    fun signIn(username: String, password: String) {
        // Do NOT use postValue(), since it will update the value asynchronously. Therefore, loading may not be seen by
        // the view just after setting it.
        signInLiveData.value = Loading()
        disposable = signInBufferoosUseCase.execute(SignInParams(username, password))
            .subscribe({
                signInLiveData.value = Success(it.id)
            }, {
                signInLiveData.value = Error(errorBundleBuilder.build(it, SIGN_IN))
            })
    }
}