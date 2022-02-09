package com.nativedevps.support.utility.device

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import nativedevps.support.R
import java.io.File

object Common {
    fun Activity.shareText(description: String) {

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND
        val intent = Intent(Intent.ACTION_SEND)

        // setting type of data shared as text
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")

        // Adding the text to share using putExtra
        intent.putExtra(Intent.EXTRA_TEXT, description)
        ContextCompat.startActivity(this, Intent.createChooser(intent, "Share Via"), null)
    }

    fun Context.shareFileText(
        fileList: List<File>? = null,
        fileMimeType: Array<String> = arrayOf("text/*"),
        emailAddress: String? = null,
        emailSubject: String = "YOUR_SUBJECT_HERE - ${this.getString(R.string.app_name)}",
        description: String = "",
    ) {
        val intentShareFile = Intent(Intent.ACTION_SEND)
        if (fileList != null) {
            val uriList = arrayListOf<Uri>()
            fileList.forEach { file ->
                uriList.add(
                    FileProvider.getUriForFile(
                        this@shareFileText,
                        "${this@shareFileText.packageName}.provider",
                        file
                    )
                )
            }
            intentShareFile.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
            intentShareFile.type = "*/*"
        } else {
            intentShareFile.type = "text/*"
        }
        intentShareFile.action = Intent.ACTION_SEND_MULTIPLE;
        intentShareFile.putExtra(Intent.EXTRA_MIME_TYPES, fileMimeType)
        emailAddress?.let {
            intentShareFile.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress));
            intentShareFile.putExtra(
                Intent.EXTRA_SUBJECT,
                emailSubject
            )
        }

        intentShareFile.putExtra(
            Intent.EXTRA_TEXT, description
        );
        startActivity(
            Intent.createChooser(intentShareFile, null).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}