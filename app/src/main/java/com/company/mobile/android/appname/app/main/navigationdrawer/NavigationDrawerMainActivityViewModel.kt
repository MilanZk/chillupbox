package com.company.mobile.android.appname.app.main.navigationdrawer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.mobile.android.appname.app.common.exception.AppAction.SIGN_OUT
import com.company.mobile.android.appname.app.common.exception.ErrorBundleBuilder
import com.company.mobile.android.appname.app.common.model.ResourceState
import com.company.mobile.android.appname.app.common.model.ResourceState.Loading
import com.company.mobile.android.appname.app.common.model.ResourceState.Success
import com.company.mobile.android.appname.app.common.model.ResourceState.Error
import com.company.mobile.android.appname.domain.bufferoo.interactor.SignOutBufferoos
import io.reactivex.disposables.Disposable
import kotlin.properties.Delegates

typealias NavigationDrawerSignOutState = ResourceState<Void?>

class NavigationDrawerMainActivityViewModel(private val signOutBufferoosUseCase: SignOutBufferoos, private val errorBundleBuilder: ErrorBundleBuilder) : ViewModel() {
    lateinit var currentSectionFragmentTag: String
    var currentMenuItemId: Int by Delegates.notNull()

    private val navigationDrawerSignOutLiveData: MutableLiveData<NavigationDrawerSignOutState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun getSignOut(): LiveData<NavigationDrawerSignOutState> {
        return navigationDrawerSignOutLiveData
    }

    fun signOut() {
        // Do NOT use postValue(), since it will update the value asynchronously. Therefore, loading may not be seen by
        // the view just after setting it.
        navigationDrawerSignOutLiveData.value = Loading()
        disposable = signOutBufferoosUseCase.execute()
            .subscribe({
                navigationDrawerSignOutLiveData.value = Success(null)
            }, {
                navigationDrawerSignOutLiveData.value = Error(errorBundleBuilder.build(it, SIGN_OUT))
            })
    }
}
