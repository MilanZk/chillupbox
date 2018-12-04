package org.buffer.android.boilerplate.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.remote.mapper.BufferooEntityMapper
import org.buffer.android.boilerplate.remote.test.factory.BufferooFactory
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