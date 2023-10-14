package com.nativedevps.support.coroutines

import kotlinx.coroutines.CancellationException

suspend fun <T : Any> getResult(data: suspend () -> Result<T>): NetworkResult<T> {
    return try {
        resultOf { data().getOrThrow() }
    } catch (e: Exception) {
        ErrorApiResult(e.localizedMessage ?: "Error occurred")
    }
}

inline fun <R> resultOf(block: () -> R): NetworkResult<R> {
    return try {
        SuccessApiResult(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        ErrorApiResult(e.message.toString())
    }
}