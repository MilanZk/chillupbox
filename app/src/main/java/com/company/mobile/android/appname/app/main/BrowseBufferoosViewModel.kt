package com.company.mobile.android.appname.app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.mobile.android.appname.app.main.BrowseState.Error
import com.company.mobile.android.appname.app.main.BrowseState.Loading
import com.company.mobile.android.appname.app.main.BrowseState.Success
import com.company.mobile.android.appname.domain.bufferoo.interactor.GetBufferoos
import io.reactivex.disposables.Disposable

class BrowseBufferoosViewModel(val getBufferoos: GetBufferoos) : ViewModel() {

    private val bufferoosLiveData: MutableLiveData<BrowseState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getBufferoos(): LiveData<BrowseState> {
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