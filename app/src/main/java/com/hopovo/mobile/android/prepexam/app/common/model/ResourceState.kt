package com.hopovo.mobile.android.prepexam.app.common.model

import com.hopovo.mobile.android.prepexam.app.common.errorhandling.ErrorBundle

/**
 * A generic class that serves as default implementation for a resource state class.
 *
 * If you need a more specific class, create a copy, remove generic parameter, customize to your like and place in
 * inside the same package of the view model where it is used.
 */
sealed class ResourceState<out T> {

    class Loading<T> : ResourceState<T>() // Loading can NOT be an object inside a class with a generic type
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error<T>(val errorBundle: ErrorBundle) : ResourceState<T>()
}
