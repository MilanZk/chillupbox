package com.company.mobile.android.appname.app.bufferoos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosNavigationCommand
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import com.company.mobile.android.appname.app.common.model.ResourceState
import com.company.mobile.android.appname.app.common.model.ResourceState.Error
import com.company.mobile.android.appname.app.common.model.ResourceState.Loading
import com.company.mobile.android.appname.app.common.model.ResourceState.Success
import com.company.mobile.android.appname.app.common.viewmodel.CommonEventsViewModel
import com.company.mobile.android.appname.app.common.viewmodel.SingleLiveEvent
import com.company.mobile.android.appname.domain.bufferoo.interactor.GetBufferoos
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import io.reactivex.disposables.Disposable

typealias BufferoosState = ResourceState<List<Bufferoo>>

class BufferoosViewModel(private val getBufferoosUseCase: GetBufferoos) : CommonEventsViewModel() {

    private val bufferoosLiveData: MutableLiveData<BufferoosState> = MutableLiveData()
    private val selectedBufferooLiveData: MutableLiveData<Bufferoo> = MutableLiveData()
    private var bufferoos: List<Bufferoo> = emptyList()
    private var disposable: Disposable? = null
    val bufferoosNavigationLiveEvent = SingleLiveEvent<BufferoosNavigationCommand>()

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    fun getBufferoos(): LiveData<BufferoosState> {
        return bufferoosLiveData
    }

    fun getSelectedBufferoo(): LiveData<Bufferoo> {
        return selectedBufferooLiveData
    }

    fun select(position: Int) {
        // Store selected item for details view
        selectedBufferooLiveData.value = bufferoos[position]
        // Go to details view
        bufferoosNavigationLiveEvent.value = GoToDetailsView
    }

    fun fetchBufferoos() {
        // Do NOT use postValue(), since it will update the value asynchronously. Therefore, loading may not be seen by
        // the view just after setting it.
        bufferoosLiveData.value = Loading()
        disposable = getBufferoosUseCase.execute()
            .subscribeWith(
                object : SingleRemoteInterceptor<List<Bufferoo>>(commonLiveEvent) {
                    override fun onSuccess(bufferoos: List<Bufferoo>) {
                        this@BufferoosViewModel.bufferoos = bufferoos
                        bufferoosLiveData.value = Success(bufferoos)
                    }

                    override fun onRegularError(e: Throwable) {
                        bufferoosLiveData.value = Error(e.message ?: "")
                    }
                }
            )
    }
}