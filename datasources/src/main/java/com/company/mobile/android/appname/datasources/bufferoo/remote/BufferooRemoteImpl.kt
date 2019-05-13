package com.company.mobile.android.appname.datasources.bufferoo.remote

import com.company.mobile.android.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.data.bufferoo.source.BufferooDataStore
import com.company.mobile.android.appname.datasources.bufferoo.remote.mapper.BufferooEntityMapper
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class BufferooRemoteImpl constructor(
    private val bufferooService: BufferooService,
    private val entityMapper: BufferooEntityMapper
) : BufferooDataStore {

    /**
     * Retrieve a list of [Bufferoo] instances from the [BufferooService].
     */
    override fun getBufferoos(): Single<List<Bufferoo>> {
        return bufferooService.getBufferoos()
            .map { it.team }
            .map {
                val entities = mutableListOf<Bufferoo>()
                it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                entities
            }
    }

    override fun clearBufferoos(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isValidCache(): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}