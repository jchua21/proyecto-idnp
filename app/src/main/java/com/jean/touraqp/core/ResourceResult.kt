package com.jean.touraqp.core

sealed class ResourceResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T? = null, message: String) : ResourceResult<T>(data, message)
    class Error<T>(data: T? = null, message: String?) : ResourceResult<T>(data, message)
    class Loading<T>(message: String? = null) : ResourceResult<T>(message = message)
}