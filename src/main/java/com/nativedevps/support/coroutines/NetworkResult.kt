package com.nativedevps.support.coroutines

sealed class NetworkResult<out T>

data class ErrorApiResult<out T>(
    val message: String,
    val throwable: Throwable? = null,
) : NetworkResult<T>()

data class StatusApiResult<out T>(
    val state: Status,
) : NetworkResult<T>()

data class SuccessApiResult<out T>(
    val data: T,
) : NetworkResult<T>()

sealed class Status
data class Information<T>(val message: String, val result: T) : Status()
data class Error<out T>(val exception: T) : Status()
data class Progress<out T>(val progress: T) : Status()