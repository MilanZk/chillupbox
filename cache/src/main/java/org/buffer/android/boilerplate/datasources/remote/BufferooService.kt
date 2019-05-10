package org.buffer.android.boilerplate.datasources.remote

import io.reactivex.Single
import org.buffer.android.boilerplate.datasources.remote.model.BufferooModel
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface BufferooService {

    @GET("users")
    fun getBufferoos(): Single<BufferooResponse>

    class BufferooResponse {
        lateinit var team: List<BufferooModel>
    }
}