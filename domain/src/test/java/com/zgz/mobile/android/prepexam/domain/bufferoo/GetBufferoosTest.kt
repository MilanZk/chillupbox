package com.zgz.mobile.android.prepexam.domain.bufferoo

import com.zgz.mobile.android.prepexam.domain.bufferoo.interactor.GetBufferoos
import com.zgz.mobile.android.prepexam.domain.bufferoo.repository.BufferooRepository
import com.zgz.mobile.android.prepexam.domain.executor.PostExecutionThread
import com.zgz.mobile.android.prepexam.domain.executor.ThreadExecutor
import com.zgz.mobile.android.prepexam.domain.test.factory.BufferooFactory
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.*

class GetBufferoosTest {

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val mockBufferooRepository = mock<BufferooRepository>()

    private val getBufferoos = GetBufferoos(
        mockBufferooRepository, mockThreadExecutor,
        mockPostExecutionThread
    )

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getBufferoos.buildUseCaseObservable(null)
        verify(mockBufferooRepository).getBufferoos()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubBufferooRepositoryGetBufferoos(Single.just(BufferooFactory.makeBufferooList(2)))
        val testObserver = getBufferoos.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val bufferoos = BufferooFactory.makeBufferooList(2)
        stubBufferooRepositoryGetBufferoos(Single.just(bufferoos))
        val testObserver = getBufferoos.buildUseCaseObservable(null).test()
        testObserver.assertValue(bufferoos)
    }

    private fun stubBufferooRepositoryGetBufferoos(single: Single<List<Bufferoo>>) {
        whenever(mockBufferooRepository.getBufferoos())
            .thenReturn(single)
    }
}