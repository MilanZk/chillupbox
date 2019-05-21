package com.company.mobile.android.appname.datasources.bufferoo.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.datasources.bufferoo.remote.mapper.BufferooEntityMapper
import com.company.mobile.android.appname.datasources.bufferoo.remote.test.factory.BufferooFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class BufferooRemoteImplTest {

    private val entityMapper = mock<BufferooEntityMapper>()
    private val bufferooService = mock<BufferooService>()

    private val bufferooRemoteImpl = BufferooRemoteImpl(bufferooService, entityMapper)

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        stubBufferooServiceGetBufferoos(Single.just(BufferooFactory.makeBufferooResponse()))
        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        val bufferooResponse = BufferooFactory.makeBufferooResponse()
        stubBufferooServiceGetBufferoos(Single.just(bufferooResponse))
        val bufferooEntities = mutableListOf<Bufferoo>()
        bufferooResponse.team.forEach {
            bufferooEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertValue(bufferooEntities)
    }
    //</editor-fold>

    private fun stubBufferooServiceGetBufferoos(
        observable:
        Single<BufferooService.BufferooResponse>
    ) {
        whenever(bufferooService.getBufferoos())
            .thenReturn(observable)
    }
}