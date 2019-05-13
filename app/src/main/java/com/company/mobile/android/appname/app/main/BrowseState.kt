package com.company.mobile.android.appname.app.main

import com.company.mobile.android.appname.app.model.ResourceState
import com.company.mobile.android.appname.model.bufferoo.Bufferoo

sealed class BrowseState(
    val resourceState: ResourceState,
    val data: List<Bufferoo>? = null,
    val errorMessage: String? = null
) {

    data class Success(private val bufferoos: List<Bufferoo>) : BrowseState(
        ResourceState.SUCCESS,
        bufferoos
    )

    data class Error(private val message: String? = null) : BrowseState(
        ResourceState.SUCCESS,
        errorMessage = message
    )

    object Loading : BrowseState(ResourceState.LOADING)
}