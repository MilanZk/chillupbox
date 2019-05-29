package com.company.mobile.android.appname.app.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.mobile.android.appname.app.common.model.ResourceState
import com.company.mobile.android.appname.app.common.model.ResourceState.Error
import com.company.mobile.android.appname.app.common.model.ResourceState.Loading
import com.company.mobile.android.appname.app.common.model.ResourceState.Success
import com.company.mobile.android.appname.domain.bufferoo.interactor.SignInBufferoos
import com.company.mobile.android.appname.domain.bufferoo.interactor.SignInBufferoos.SignInParams
import io.reactivex.disposables.Disposable

typealias SignInState = ResourceState<String>

class SignInViewModel(val signInBufferoosUseCase: SignInBufferoos) : ViewModel() {

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
                signInLiveData.value = Error(it.message ?: "")
            })
    }
}