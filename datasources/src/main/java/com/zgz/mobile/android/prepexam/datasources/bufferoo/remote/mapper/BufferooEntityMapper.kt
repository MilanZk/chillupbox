package com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.mapper

import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.model.BufferooResponse
import com.zgz.mobile.android.prepexam.datasources.remote.mapper.EntityMapper
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo

/**
 * Map a [BufferooResponse] to and from a [Bufferoo] instance when data is moving between
 * this later and the Data layer
 */
open class BufferooEntityMapper : EntityMapper<BufferooResponse, Bufferoo> {

    /**
     * Map an instance of a [BufferooResponse] to a [Bufferoo] model
     */
    override fun mapFromRemote(type: BufferooResponse): Bufferoo {
        return Bufferoo(type.id, type.name, type.title, type.avatar)
    }
}