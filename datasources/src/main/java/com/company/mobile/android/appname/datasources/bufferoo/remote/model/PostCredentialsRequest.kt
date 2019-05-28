package com.company.mobile.android.appname.datasources.bufferoo.remote.model

import com.google.gson.annotations.SerializedName

data class PostCredentialsRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)