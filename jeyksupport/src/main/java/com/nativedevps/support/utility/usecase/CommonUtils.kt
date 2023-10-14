package com.nativedevps.support.utility.usecase

import android.net.Network
import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.coroutines.StatusApiResult
import com.nativedevps.support.coroutines.SuccessApiResult
import com.nativedevps.support.utility.threading.runOnSameThread
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/*todo: handle by suspendable execution*/
suspend fun <T> Flow<NetworkResult<T>>.await(): T {
    return when (val result = first()) {
        is SuccessApiResult -> result.data
        is ErrorApiResult -> throw result.throwable ?: Exception(result.message)
        else -> error("not handled a $result this api")
    }
}

suspend fun <T, I> Flow<NetworkResult<T>>.await(producerScope: ProducerScope<NetworkResult<I>>?): T {
    return suspendCancellableCoroutine { cancellableContinuation ->
        val job = runOnSameThread {
            collectLatest { result ->
                when (result) {
                    is SuccessApiResult -> cancellableContinuation.resume(result.data)
                    is ErrorApiResult -> cancellableContinuation.resumeWithException(result.throwable ?: Exception(result.message))
                    is StatusApiResult -> producerScope?.trySend(result as NetworkResult<I>)
                        ?: error("handle status api result")

                    else -> error("not handled a $result this api")
                }
            }
        }
    }
}

fun <T> emulate(execution: suspend (ProducerScope<NetworkResult<T>>) -> T) = channelFlow<NetworkResult<T>> {
    try {
        trySend(SuccessApiResult(execution(this)))
    } catch (e: Exception) {
        trySend(ErrorApiResult(e.message ?: "execute with debug", e))
    }
}