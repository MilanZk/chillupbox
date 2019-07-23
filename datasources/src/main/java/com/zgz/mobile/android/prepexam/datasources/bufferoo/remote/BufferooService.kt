package com.zgz.mobile.android.prepexam.datasources.bufferoo.remote

import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.model.BufferooResponse
import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.model.PostCredentialsRequest
import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.model.PostCredentialsResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface BufferooService {

    companion object {
        const val BASE_URL = "https://5178af27-2e3e-481f-ad80-7c5e7703f0ee.mock.pstmn.io"
    }

    class BufferoosResponse {
        lateinit var items: List<BufferooResponse>
    }

    @POST("oauth/token")
    fun postCredentials(@Body postCredentialsRequest: PostCredentialsRequest): Single<PostCredentialsResponse>

    @GET("items")
    fun getBufferoos(): Single<BufferoosResponse>
}