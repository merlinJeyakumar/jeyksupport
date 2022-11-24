package com.nativedevps.support.utility.usecase

import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.coroutines.SuccessApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

suspend fun <T> Flow<NetworkResult<T>>.await(): T {
    return when (val result = first()) {
        is SuccessApiResult -> result.data
        is ErrorApiResult -> error(result.message)
        else -> error("not handled a status api result")
    }
}