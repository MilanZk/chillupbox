package com.hopovo.mobile.android.prepexam.app.bufferoos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.Error
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.Loading
import com.hopovo.mobile.android.prepexam.app.common.model.ResourceState.Success
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.CommonEvent
import com.hopovo.mobile.android.prepexam.app.common.viewmodel.SingleLiveEvent
import com.hopovo.mobile.android.prepexam.app.test.util.BufferooFactory
import com.hopovo.mobile.android.prepexam.app.test.util.DataFactory
import com.hopovo.mobile.android.prepexam.domain.bufferoo.interactor.GetExercises
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.*
import java.util.concurrent.TimeUnit

class ExerciseListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mockGetBufferoos = mock<GetExercises>()
    private val commonLiveEvent = mock<SingleLiveEvent<CommonEvent>>()

    private val testErrorBundleBuilder = TestErrorBundleBuilder()
    private val bufferoosViewModel = ExerciseListViewModel(mockGetBufferoos, testErrorBundleBuilder)

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

    private fun stubGetBufferoos(single: Single<List<Exercise>>) {
        // Note: do not use mockGetBufferoos.execute(any()), it will not work. If there are no parameters, that is
        // use case params is Void, do not use any()
        whenever(mockGetBufferoos.execute())
            .thenReturn(single)
    }
}