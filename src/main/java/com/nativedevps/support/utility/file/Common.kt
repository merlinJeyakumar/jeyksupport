package com.nativedevps.support.utility.file

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.nativedevps.support.utility.debugging.JLogE
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.net.URLConnection


fun getFileMime(file: File): String? {
    return try {
        URLConnection.guessContentTypeFromStream(
            BufferedInputStream(FileInputStream(file))
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getFileExtensionMime(mimeType: String): String? {
    return try {
        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Uri.getFilePath(context: Context): String? {
    return try {
        RealPathUtil.getRealPathFromURI(context, this)
    } catch (e: Exception) {
        JLogE("FileUri: ", e.localizedMessage)
        null
    }
}