package com.hopovo.mobile.android.prepexam.datasources.exercise.remote

import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.model.ExerciseResponse
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.model.PostCredentialsRequest
import com.hopovo.mobile.android.prepexam.datasources.exercise.remote.model.PostCredentialsResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Defines the abstract methods used for interacting with the Exercise API
 */
interface BufferooService {

    companion object {
        const val BASE_URL = "https://81d6b817-f4a2-4b46-bfa3-6e1a739140f1.mock.pstmn.io"
    }

    class BufferoosResponse {
        lateinit var items: List<ExerciseResponse>
    }

    @POST("oauth/token")
    fun postCredentials(@Body postCredentialsRequest: PostCredentialsRequest): Single<PostCredentialsResponse>

    @GET("exercises")
    fun getBufferoos(): Single<BufferoosResponse>
}