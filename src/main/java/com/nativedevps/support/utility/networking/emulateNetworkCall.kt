package com.nativedevps.support.utility.networking


import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.coroutines.SuccessApiResult
import com.nativedevps.support.inline.orElse
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import org.json.JSONObject
import retrofit2.HttpException


/**
 * Introduced for reducing boilerplate code reducing
 * Declared generic type executed and collected with {@link ChannelFlow}
 *
 * @return channelFlow with {@link NetworkResult} including success result/failure exception
 */
fun <T> emulate(
    execution: suspend (ProducerScope<NetworkResult<T>>) -> NetworkResult<T>
) = channelFlow<NetworkResult<T>> {
        try {
            trySend(execution(this))
        } catch (e: Exception) {
            trySend(ErrorApiResult(e.message ?: "execute with debug", e))
            e.printStackTrace()
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
fun Context.isDarkMode(): Boolean {
    val darkModeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
}

fun Context.toast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

fun attempt(callback: () -> Unit) {
    try {
        callback()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}