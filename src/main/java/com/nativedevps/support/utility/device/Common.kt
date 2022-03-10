package com.nativedevps.support.utility.device

import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import nativedevps.support.R
import java.io.File

object Common {
    fun Activity.shareText(
        description: String,
        subject: String? = null,
    ) {

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND
        val intent = Intent(Intent.ACTION_SEND)

        // setting type of data shared as text
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

        // Adding the text to share using putExtra
        intent.putExtra(Intent.EXTRA_TEXT, description)
        ContextCompat.startActivity(this, Intent.createChooser(intent, "Share Via"), null)
    }

    fun Context.shareFile(
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
                        this@shareFile,
                        "${this@shareFile.packageName}.provider",
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

    fun Activity.startCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    fun getAvailableMemory(): Long {
        val runTime = Runtime.getRuntime()
        val usedMemInMB = (runTime.totalMemory() - runTime.freeMemory()) / 1048576L;
        val maxHeapSizeInMB = runTime.maxMemory() / 1048576L;
        return maxHeapSizeInMB - usedMemInMB;
    }

    fun Context.openLinkInternally(text: String) {
        val defaultBrowser =
            Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        defaultBrowser.data = Uri.parse(text)
        ContextCompat.startActivity(this, defaultBrowser, null)
    }

    fun Context.openLinkExternally(text: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
        ContextCompat.startActivity(this, browserIntent, null)
    }

    fun Context.pasteClipboardText(): String? {
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = clipboard.primaryClip ?: return null
        val item = clip.getItemAt(0) ?: return null
        return item.text.toString() ?: return null
    }

    fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun Context.isAppInstalled(packageName: String): Boolean {
        val pm: PackageManager = packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun openURL(context: Context, url: String) {
        var url = url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun Context.isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability: GoogleApiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode: Int = apiAvailability.isGooglePlayServicesAvailable(this)
        return connectionStatusCode == ConnectionResult.SUCCESS
    }

    fun Context.acquireGooglePlayServices(): Pair<Boolean,Int> {
        val apiAvailability: GoogleApiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode: Int = apiAvailability.isGooglePlayServicesAvailable(this)
        return Pair(apiAvailability.isUserResolvableError(connectionStatusCode),
            connectionStatusCode)
    }
}