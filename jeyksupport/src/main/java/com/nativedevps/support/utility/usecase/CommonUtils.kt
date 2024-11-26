package com.nativedevps.support.utility.usecase

import android.net.Network
import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.coroutines.StatusApiResult
import com.nativedevps.support.coroutines.SuccessApiResult
import com.nativedevps.support.inline.orElse
import com.nativedevps.support.utility.threading.runOnSameThread
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import retrofit2.HttpException
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


/**
 * Introduced for reducing boilerplate code reducing
 * Declared generic type executed and collected with {@link ChannelFlow}
 *
 * @return channelFlow with {@link NetworkResult} including success result/failure exception
 */
fun <T> emulate(execution: suspend (ProducerScope<NetworkResult<T>>) -> NetworkResult<T>) =
    channelFlow<NetworkResult<T>> {
        try {
            trySend(execution(this))
        } catch (e: Exception) {
            trySend(ErrorApiResult(e.message ?: "execute with debug", e))
        }
    }


/**
 * Result with generic type are called with {@link getOrThrow} method
 *
 *  @return In case of Success it will returned within the method
 *  @return In case of Failure {@link errorapiresult} with exception returned
 * */
suspend fun <T> Result<T>.emulateNetworkCall(
    onSuccess: (suspend (T) -> Unit)? = null,
    onFailure: (suspend (HttpException) -> Unit)? = null,
): NetworkResult<T> {
    return try {
        val result = getOrThrow()
        onSuccess?.invoke(result)
        (SuccessApiResult(result))

    } catch (httpException: HttpException) {
        httpException.printStackTrace()
        val exception = try {
            httpException.response()?.errorBody()?.string()
                ?.let { JSONObject(it).getString("message") }.orElse {
                    httpException.response()?.errorBody()?.string()
                } ?: httpException.message ?: "error not defined"
        } catch (e: Exception) {
            httpException.message ?: "error not defined"
        }
        onFailure?.invoke(httpException)
        ErrorApiResult(exception)
    }
}