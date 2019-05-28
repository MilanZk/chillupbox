package com.company.mobile.android.appname.datasources.bufferoo.remote.test.factory

import com.company.mobile.android.appname.datasources.bufferoo.remote.BufferooService
import com.company.mobile.android.appname.datasources.bufferoo.remote.model.BufferooResponse
import com.company.mobile.android.appname.datasources.bufferoo.remote.test.factory.DataFactory.Factory.randomLong
import com.company.mobile.android.appname.datasources.bufferoo.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferoosResponse(): BufferooService.BufferoosResponse {
            val bufferoosResponse = BufferooService.BufferoosResponse()
            bufferoosResponse.team = makeBufferooResponseList(5)
            return bufferoosResponse
        }

        fun makeBufferooResponseList(count: Int): List<BufferooResponse> {
            val bufferooEntities = mutableListOf<BufferooResponse>()
            repeat(count) {
                bufferooEntities.add(makeBufferooResponse())
            }
            return bufferooEntities
        }

        fun makeBufferooResponse(): BufferooResponse {
            return BufferooResponse(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }
    }
}