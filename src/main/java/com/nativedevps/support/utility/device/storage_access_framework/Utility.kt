package com.nativedevps.support.utility.device.storage_access_framework

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.DocumentsContract
import androidx.core.content.FileProvider
import com.nativedevps.support.utility.device.mime.MimeWildCard.MIME_IMAGE_WILD_CARD
import org.jetbrains.anko.toast
import java.io.File

fun Activity.showGalleryPicker(mime: String = MIME_IMAGE_WILD_CARD, reqCode: Int) {
    if (Build.VERSION.SDK_INT >= 19) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = mime
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, reqCode)
    } else {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = mime
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, reqCode)
    }
}


fun Context.openInFolderBrowser(file: File) {
    val intent = Intent(Intent.ACTION_VIEW)
    val myDir = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        intent.setDataAndType(myDir, DocumentsContract.Document.MIME_TYPE_DIR)
    else {
        intent.setDataAndType(myDir, "*/*")
    }

    if (intent.resolveActivityInfo(packageManager, 0) != null) {
        startActivity(intent)
    } else {
        toast("Couldn't find file browser")
    }
}