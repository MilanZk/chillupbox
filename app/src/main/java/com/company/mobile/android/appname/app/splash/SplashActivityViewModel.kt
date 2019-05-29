package com.company.mobile.android.appname.app.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.mobile.android.appname.app.common.model.ResourceState
import com.company.mobile.android.appname.app.common.model.ResourceState.Error
import com.company.mobile.android.appname.app.common.model.ResourceState.Loading
import com.company.mobile.android.appname.app.common.model.ResourceState.Success
import com.company.mobile.android.appname.domain.bufferoo.interactor.GetCredentials
import com.company.mobile.android.appname.model.bufferoo.Credentials
import io.reactivex.disposables.Disposable

typealias CredentialsState = ResourceState<Credentials>

class SplashActivityViewModel(private val getCredentialsUseCase: GetCredentials) : ViewModel() {
    private val credentialsLiveData: MutableLiveData<CredentialsState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun getCredentials(): LiveData<CredentialsState> {
        return credentialsLiveData
    }

    fun loadCredentials() {
        // Do NOT use postValue(), since it will update the value asynchronously. Therefore, loading may not be seen by
        // the view just after setting it.
        credentialsLiveData.value = Loading()
        disposable = getCredentialsUseCase.execute()
            .subscribe({
                credentialsLiveData.value = Success(it)
            }, {
                credentialsLiveData.value = Error(it.message ?: "")
            })
    }
}
