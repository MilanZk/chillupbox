package com.company.mobile.android.appname.app.bufferoos.viewmodel

import com.company.mobile.android.appname.app.common.model.ResourceState
import com.company.mobile.android.appname.model.bufferoo.Bufferoo

sealed class BufferoosState(
    val resourceState: ResourceState,
    val data: List<Bufferoo>? = null,
    val errorMessage: String? = null
) {

    data class Success(private val bufferoos: List<Bufferoo>) : BufferoosState(
        ResourceState.SUCCESS,
        bufferoos
    )

    data class Error(private val message: String? = null) : BufferoosState(
        ResourceState.SUCCESS,
        errorMessage = message
    )

    object Loading : BufferoosState(ResourceState.LOADING)
}