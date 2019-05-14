package com.company.mobile.android.appname.app.bufferoos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosState.Error
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosState.Loading
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosState.Success
import com.company.mobile.android.appname.domain.bufferoo.interactor.GetBufferoos
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.app.common.viewmodel.SingleLiveEvent
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosNavigationCommand
import com.company.mobile.android.appname.app.bufferoos.master.BufferoosNavigationCommand.GoToDetailsView
import io.reactivex.disposables.Disposable

class BufferoosViewModel(val getBufferoos: GetBufferoos) : ViewModel() {

    private val bufferoosLiveData: MutableLiveData<BufferoosState> = MutableLiveData()
    private val selectedBufferooLiveData: MutableLiveData<Bufferoo> = MutableLiveData()
    private var bufferoos: List<Bufferoo> = emptyList()
    private var disposable: Disposable? = null
    val bufferoosNavigationEvent = SingleLiveEvent<BufferoosNavigationCommand>()

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getBufferoos(): LiveData<BufferoosState> {
        return bufferoosLiveData
    }

    fun select(position: Int) {
        // Store selected item for details view
        selectedBufferooLiveData.value = bufferoos[position]
        // Go to details view
        bufferoosNavigationEvent.value = GoToDetailsView
    }

    fun fetchBufferoos() {
        bufferoosLiveData.postValue(Loading)
        disposable = getBufferoos.execute()
            .subscribe({
                bufferoos = it
                bufferoosLiveData.postValue(Success(bufferoos))
            }, {
                bufferoosLiveData.postValue(Error(it.message))
            })
    }

}