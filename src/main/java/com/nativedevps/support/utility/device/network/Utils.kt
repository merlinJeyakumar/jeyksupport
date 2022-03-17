package com.nativedevps.support.utility.device.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.Util
import okio.BufferedSink
import okio.BufferedSource
import okio.Okio
import java.io.File
import java.io.IOException
import java.util.*


@SuppressLint("MissingPermission")
fun isInternetConnected(context: Context): Boolean {
    val connectivity = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivity != null) {
        val nInfo = connectivity.activeNetworkInfo
        if (nInfo != null) {
            //do your thing
            if (nInfo.type == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true
            } else if (nInfo.type == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true
            }
        }
        return false
    }
    return false
}


@SuppressLint("MissingPermission")
fun Context.isInternetAvailable(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var netInfo: NetworkInfo? = null
    netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnected && netInfo.isAvailable
}

@SuppressLint("MissingPermission")
fun Context.isConnectedToMobileData(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(TRANSPORT_CELLULAR)
    } else {
        val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.type == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected
    }
}

@SuppressLint("MissingPermission")
fun Context.isConnectedToWifi(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return false
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    } else {
        val networkInfo =
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                ?: return false
        networkInfo.isConnected
    }
}

/**
 * @param url:      donwload url
 * @param destFile: output path
 * @return Rxjava.Observable
 */
fun okioFileDownload(url: String, destFile: File): Observable<Int>? {
    return Observable.create<Int> { emitter ->
        var sink: BufferedSink? = null
        var source: BufferedSource? = null
        val lastProgress = 0
        try {
            val request = Request.Builder().url(url).build()
            val response = OkHttpClient().newCall(request).execute()
            val body = response.body()
            val contentLength = body!!.contentLength()
            source = body.source()
            sink = Okio.buffer(Okio.sink(destFile))
            val sinkBuffer = sink.buffer()
            var totalBytesRead: Long = 0
            val bufferSize = 6 * 1024
            var bytesRead: Long
            while (source.read(sinkBuffer, bufferSize.toLong()).also { bytesRead = it } != -1L) {
                sink.emit()
                totalBytesRead += bytesRead
                val progress = (totalBytesRead * 100 / contentLength).toInt()
                if (lastProgress != progress) { //reduce_redundant_callback
                    emitter.onNext(progress)
                }
            }
            sink.flush()
        } catch (e: IOException) {
            Log.e("@@@", "IOException --- ", e)
            emitter.onError(e)
        } finally {
            Util.closeQuietly(sink)
            Util.closeQuietly(source)
        }
        emitter.onComplete()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}