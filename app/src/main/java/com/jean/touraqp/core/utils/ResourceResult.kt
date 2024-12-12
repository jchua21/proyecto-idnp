package com.jean.touraqp.core.utils

sealed class ResourceResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T? = null, message: String?) : ResourceResult<T>(data, message)
    class Error<T>(data: T? = null, message: String?) : ResourceResult<T>(data, message)
    class Loading<T>(message: String? = null) : ResourceResult<T>(message = message)
}

// Result 2.0 :,V
sealed class Result<out D, out E: Exception>{
    data class Success<out D>(val data: D) : Result<D, Nothing>()
    data class Error<out E: Exception>(val error: E): Result<Nothing, E>()
}

inline fun <D, E: Exception> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D,E>{
    return when(this){
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <D, E: Exception> Result<D,E>.onError(action: (E) -> Unit): Result<D,E> {
    return when(this){
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}