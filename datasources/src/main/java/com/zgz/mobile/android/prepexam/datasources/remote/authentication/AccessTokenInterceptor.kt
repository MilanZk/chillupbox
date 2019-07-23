package com.zgz.mobile.android.prepexam.datasources.remote.authentication

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.token()

        val authHeaderContent = if (token == null || TextUtils.isEmpty(token)) {
            "Basic ${tokenProvider.basicCredentials()}"
        } else {
            "Bearer $token"
        }

        val authenticatedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", authHeaderContent)
            .build()

        return chain.proceed(authenticatedRequest)
    }
}