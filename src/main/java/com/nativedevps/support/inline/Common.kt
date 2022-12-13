package com.nativedevps.support.inline

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.google.gson.Gson
import com.nativedevps.support.utility.device.Common.shareText

import nativedevps.support.R
import java.util.*

fun Any.toJson(): String? {
    try {
        return Gson().toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun Context.getMimeType(uri: Uri): String? {
    var mimeType: String? = null
    mimeType = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        contentResolver.getType(uri)
    } else {
        val fileExtension: String = MimeTypeMap.getFileExtensionFromUrl(
            uri
                .toString()
        )
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.toLowerCase(Locale.getDefault())
        )
    }
    return mimeType
}

fun Context.getSharingText(): String {
    return "Excited to share it from *${
        this.resources.getString(
            R.string.app_name
        )
    }* App,\nDownload an app from PlayStore ${getPlayStoreUrl()}"
}

fun Context.getPlayStoreUrl(): String {
    return "https://play.google.com/store/apps/details?id=" + applicationContext.packageName
}

fun Activity.sharePlayStoreUrl() {
    shareText(description = getSharingText())
}

fun String?.orNull(): String {
    return this ?: "null"
}

fun Any?.isNotNull(): Boolean {
    return this != null
}