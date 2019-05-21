package com.company.mobile.android.appname.datasources.bufferoo.remote.mapper

import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.datasources.bufferoo.remote.model.BufferooModel

/**
 * Map a [BufferooModel] to and from a [Bufferoo] instance when data is moving between
 * this later and the Data layer
 */
open class BufferooEntityMapper : EntityMapper<BufferooModel, Bufferoo> {

    /**
     * Map an instance of a [BufferooModel] to a [Bufferoo] model
     */
    override fun mapFromRemote(type: BufferooModel): Bufferoo {
        return Bufferoo(type.id, type.name, type.title, type.avatar)
    }
}