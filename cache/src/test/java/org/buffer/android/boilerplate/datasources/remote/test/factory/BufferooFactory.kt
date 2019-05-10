package org.buffer.android.boilerplate.datasources.remote.test.factory

import org.buffer.android.boilerplate.datasources.remote.BufferooService
import org.buffer.android.boilerplate.datasources.remote.model.BufferooModel
import org.buffer.android.boilerplate.datasources.remote.test.factory.DataFactory.Factory.randomLong
import org.buffer.android.boilerplate.datasources.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferooResponse(): BufferooService.BufferooResponse {
            val bufferooResponse = BufferooService.BufferooResponse()
            bufferooResponse.team = makeBufferooModelList(5)
            return bufferooResponse
        }

        fun makeBufferooModelList(count: Int): List<BufferooModel> {
            val bufferooEntities = mutableListOf<BufferooModel>()
            repeat(count) {
                bufferooEntities.add(makeBufferooModel())
            }
            return bufferooEntities
        }

        fun makeBufferooModel(): BufferooModel {
            return BufferooModel(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }
    }
}