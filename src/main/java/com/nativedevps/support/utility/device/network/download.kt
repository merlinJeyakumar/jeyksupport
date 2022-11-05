package com.nativedevps.support.utility.device.network

import android.content.Context
import com.nativedevps.support.utility.date_time_utility.MillisecondUtility.now
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.*
import java.io.File
import java.io.IOException
import java.lang.Exception

suspend fun Context.downloadFile(
    url: String,
    destFile: File,
    callbackProgress: (int: Int) -> Unit,
    callbackStatusUpdate: (boolean: Boolean, file: File?, error: String?) -> Unit
) {
    var sink: BufferedSink? = null
    var source: BufferedSource? = null
    val lastProgress = 0
    try {
        val request = Request.Builder().url(url).build()
        val response = OkHttpClient().newCall(request).execute()
        val body = response.body
        val contentLength = body!!.contentLength()
        source = body.source()
        sink = destFile.sink().buffer()
        val sinkBuffer = sink.buffer
        var totalBytesRead: Long = 0
        val bufferSize = 6 * 1024
        var bytesRead: Long
        while (source.read(sinkBuffer, bufferSize.toLong()).also { bytesRead = it } != -1L) {
            sink.emit()
            totalBytesRead += bytesRead
            val progress = (totalBytesRead * 100 / contentLength).toInt()
            if (lastProgress != progress) { //reduce_redundant_callback
                callbackProgress.invoke(progress)
            }
        }
        sink.flush()
        callbackStatusUpdate.invoke(true, destFile, null)
    } catch (e: Exception) {
        callbackStatusUpdate.invoke(false, null, e.localizedMessage)
    } finally {
        sink?.close()
        source?.close()
    }
}