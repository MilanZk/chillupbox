package com.company.mobile.android.appname.app.browse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.company.mobile.android.appname.app.browse.BrowseState.Error
import com.company.mobile.android.appname.app.browse.BrowseState.Loading
import com.company.mobile.android.appname.app.browse.BrowseState.Success
import com.company.mobile.android.appname.app.test.util.BufferooFactory
import com.company.mobile.android.appname.app.test.util.DataFactory
import com.company.mobile.android.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.domain.bufferoo.interactor.GetBufferoos
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.*

class BrowseBufferoosViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val mockGetBufferoos = mock<GetBufferoos>()

    private val bufferoosViewModel = BrowseBufferoosViewModel(mockGetBufferoos)

    //<editor-fold desc="Success">
    @Test
    fun getBufferoosReturnsSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value is Success)
    }

    @Test
    fun getBufferoosReturnsDataOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == list)
    }

    @Test
    fun getBufferoosReturnsNoMessageOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))

        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.errorMessage == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getBufferoosReturnsError() {
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Single.error(RuntimeException()))

        assert(bufferoosViewModel.getBufferoos().value is Error)
    }

    @Test
    fun getBufferoosFailsAndContainsNoData() {
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Single.error(RuntimeException()))

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        bufferoosViewModel.getBufferoos()
        stubGetBufferoos(Single.error(RuntimeException(errorMessage)))

        assert(bufferoosViewModel.getBufferoos().value?.errorMessage == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getBufferoosReturnsLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value is Loading)
    }

    @Test
    fun getBufferoosContainsNoDataWhenLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }

    @Test
    fun getBufferoosContainsNoMessageWhenLoading() {
        bufferoosViewModel.getBufferoos()

        assert(bufferoosViewModel.getBufferoos().value?.data == null)
    }
    //</editor-fold>

    private fun stubGetBufferoos(single: Single<List<Bufferoo>>) {
        whenever(mockGetBufferoos.execute(any()))
            .thenReturn(single)
    }
}