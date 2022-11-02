package com.nativedevps.support.coroutines


sealed class NetworkResult<out T>

data class ErrorApiResult<out T>(
    val message: String,
    val throwable: Throwable? = null
) : NetworkResult<T>()

data class StatusApiResult<out T>(
    val state: Int
) : NetworkResult<T>()

data class SuccessApiResult<out T>(
    val data: T
) : NetworkResult<T>()