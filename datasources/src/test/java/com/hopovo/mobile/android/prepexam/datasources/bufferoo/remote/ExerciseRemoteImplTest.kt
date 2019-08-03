package com.hopovo.mobile.android.prepexam.datasources.bufferoo.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.mapper.ExerciseRemoteMapper
import com.hopovo.mobile.android.prepexam.datasources.bufferoo.remote.test.factory.BufferooFactory
import com.hopovo.mobile.android.prepexam.model.exercise.Exercise
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.BufferooRemoteImpl
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.BufferooService
import io.reactivex.Single
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class ExerciseRemoteImplTest {

    private val entityMapper = mock<ExerciseRemoteMapper>()
    private val bufferooService = mock<BufferooService>()

    private val bufferooRemoteImpl = BufferooRemoteImpl(bufferooService, entityMapper, InstrumentationRegistry.getInstrumentation().targetContext)

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        stubBufferooServiceGetBufferoos(Single.just(BufferooFactory.makeBufferoosResponse()))
        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        val bufferooResponse = BufferooFactory.makeBufferoosResponse()
        stubBufferooServiceGetBufferoos(Single.just(bufferooResponse))
        val bufferooEntities = mutableListOf<Exercise>()
        bufferooResponse.items.forEach {
            bufferooEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = bufferooRemoteImpl.getBufferoos().test()
        testObserver.assertValue(bufferooEntities)
    }
    //</editor-fold>

    private fun stubBufferooServiceGetBufferoos(
        observable:
        Single<BufferooService.BufferoosResponse>
    ) {
        whenever(bufferooService.getBufferoos())
            .thenReturn(observable)
    }
}