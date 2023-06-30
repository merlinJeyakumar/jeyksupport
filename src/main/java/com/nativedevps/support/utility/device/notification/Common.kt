package com.nativedevps.support.utility.device.notification

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat

fun Context.isNotificationAllowed(): Boolean {
    return NotificationManagerCompat.from(this).isNotificationAllowed()
}


fun NotificationManagerCompat.isNotificationAllowed() = when {
    areNotificationsEnabled().not() -> false
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
        notificationChannels.firstOrNull { channel -> channel.importance == NotificationManager.IMPORTANCE_NONE } == null
    }

    else -> true
}

