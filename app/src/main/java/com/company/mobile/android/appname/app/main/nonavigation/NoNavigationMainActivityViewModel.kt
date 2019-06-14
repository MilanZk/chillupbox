package com.company.mobile.android.appname.app.main.nonavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.mobile.android.appname.app.common.errorhandling.AppAction.SIGN_OUT
import com.company.mobile.android.appname.app.common.errorhandling.ErrorBundleBuilder
import com.company.mobile.android.appname.app.common.model.ResourceState
import com.company.mobile.android.appname.app.common.model.ResourceState.Error
import com.company.mobile.android.appname.app.common.model.ResourceState.Loading
import com.company.mobile.android.appname.app.common.model.ResourceState.Success
import com.company.mobile.android.appname.domain.bufferoo.interactor.SignOutBufferoos
import io.reactivex.disposables.Disposable

typealias NoNavigationSignOutState = ResourceState<Void?>

class NoNavigationMainActivityViewModel(private val signOutBufferoosUseCase: SignOutBufferoos, private val errorBundleBuilder: ErrorBundleBuilder) : ViewModel() {

    private val signOutLiveData: MutableLiveData<NoNavigationSignOutState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun getSignOut(): LiveData<NoNavigationSignOutState> {
        return signOutLiveData
    }

    fun signOut() {
        // Do NOT use postValue(), since it will update the value asynchronously. Therefore, loading may not be seen by
        // the view just after setting it.
        signOutLiveData.value = Loading()
        disposable = signOutBufferoosUseCase.execute()
            .subscribe({
                signOutLiveData.value = Success(null)
            }, {
                signOutLiveData.value = Error(errorBundleBuilder.build(it, SIGN_OUT))
            })
    }
}
