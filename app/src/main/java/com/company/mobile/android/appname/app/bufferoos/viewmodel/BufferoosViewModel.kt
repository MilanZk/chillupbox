package com.company.mobile.android.appname.app.bufferoos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosState.Error
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosState.Loading
import com.company.mobile.android.appname.app.bufferoos.viewmodel.BufferoosState.Success
import com.company.mobile.android.appname.domain.bufferoo.interactor.GetBufferoos
import io.reactivex.disposables.Disposable

class BufferoosViewModel(val getBufferoos: GetBufferoos) : ViewModel() {

    private val bufferoosLiveData: MutableLiveData<BufferoosState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getBufferoos(): LiveData<BufferoosState> {
        return bufferoosLiveData
    }

    fun fetchBufferoos() {
        bufferoosLiveData.postValue(Loading)
        disposable = getBufferoos.execute()
            .subscribe({
                bufferoosLiveData.postValue(Success(it))
            }, {
                bufferoosLiveData.postValue(Error(it.message))
            })
    }
}