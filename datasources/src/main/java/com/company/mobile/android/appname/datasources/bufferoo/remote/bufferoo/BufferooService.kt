package com.company.mobile.android.appname.datasources.bufferoo.remote

import com.company.mobile.android.appname.datasources.bufferoo.remote.model.BufferooModel
import io.reactivex.Single
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