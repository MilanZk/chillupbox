package com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.model

import com.google.gson.annotations.SerializedName

data class PostCredentialsRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)