package com.company.mobile.android.appname.domain.bufferoo

import com.company.mobile.android.appname.domain.bufferoo.interactor.GetBufferoos
import com.company.mobile.android.appname.domain.executor.PostExecutionThread
import com.company.mobile.android.appname.domain.executor.ThreadExecutor
import com.company.mobile.android.appname.domain.bufferoo.repository.BufferooRepository
import com.company.mobile.android.appname.domain.test.factory.BufferooFactory
import com.company.mobile.android.model.bufferoo.Bufferoo
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