package com.zgz.mobile.android.prepexam.app.bufferoos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Error
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Loading
import com.zgz.mobile.android.prepexam.app.common.model.ResourceState.Success
import com.zgz.mobile.android.prepexam.app.common.viewmodel.CommonEvent
import com.zgz.mobile.android.prepexam.app.common.viewmodel.SingleLiveEvent
import com.zgz.mobile.android.prepexam.app.test.util.BufferooFactory
import com.zgz.mobile.android.prepexam.app.test.util.DataFactory
import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.GetBufferoos
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.*
import java.util.concurrent.TimeUnit

class BufferoosViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mockGetBufferoos = mock<GetBufferoos>()
    private val commonLiveEvent = mock<SingleLiveEvent<CommonEvent>>()

    private val testErrorBundleBuilder = TestErrorBundleBuilder()
    private val bufferoosViewModel = BufferoosViewModel(mockGetBufferoos, testErrorBundleBuilder)

    @Before
    fun setUp() {
        bufferoosViewModel.commonLiveEvent = commonLiveEvent
    }

    //<editor-fold desc="Success">
    @Test
    fun getBufferoosReturnsSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value is Success)
    }

    @Test
    fun getBufferoosReturnsDataOnSuccess() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list))
        bufferoosViewModel.fetchBufferoos()

        val result = bufferoosViewModel.getBufferoos().value
        Assert.assertTrue(result is Success && result.data == list)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getBufferoosReturnsError() {
        stubGetBufferoos(Single.error(RuntimeException()))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value is Error)
    }

    @Test
    fun getBufferoosFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        stubGetBufferoos(Single.error(RuntimeException(errorMessage)))
        bufferoosViewModel.fetchBufferoos()

        val result = bufferoosViewModel.getBufferoos().value
        Assert.assertTrue(result is Error && result.errorBundle.debugMessage == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getBufferoosReturnsLoading() {
        val list = BufferooFactory.makeBufferooList(2)
        stubGetBufferoos(Single.just(list).delay(60, TimeUnit.SECONDS))
        bufferoosViewModel.fetchBufferoos()

        Assert.assertTrue(bufferoosViewModel.getBufferoos().value is Loading)
    }
    //</editor-fold>

    private fun stubGetBufferoos(single: Single<List<Bufferoo>>) {
        // Note: do not use mockGetBufferoos.execute(any()), it will not work. If there are no parameters, that is
        // use case params is Void, do not use any()
        whenever(mockGetBufferoos.execute())
            .thenReturn(single)
    }
}