package com.nativedevps.support.utility.debugging

import android.content.Context
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Deprecated("use com.nativedevps.support.utility.debugging.Log.i instead")
fun Context.JLogI(message: String) {
    JLogI("Info: ${this.javaClass.simpleName}-", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.i instead")
fun JLogI(message: String) {
    JLogI("Info: ", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.i instead")
fun JLogI(tag: String, message: String) {
    Log.i(tag, message)
    CrashlyticsLog(tag,message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.i instead")
fun JLogI(message: String, throwable: Throwable) {
    JLogI("Info", throwable.localizedMessage)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.v instead")
fun Context.JLogV(message: String) {
    JLogV("Verbose: ${this.javaClass.simpleName}-", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.v instead")
fun JLogV(message: String, throwable: Throwable) {
    JLogV("Verbose", throwable.localizedMessage)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.v instead")
fun JLogV(message: String) {
    JLogV("Verbose: ", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.v instead")
fun JLogV(tag: String, message: String) {
    Log.v(tag, message)
    CrashlyticsLog(tag,message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.e instead")
fun JLogE(message: String, throwable: Throwable) {
    JLogE("Error", throwable.localizedMessage)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.e instead")
fun JLogE(message: String) {
    JLogV("Error: ", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.e instead")
fun JLogE(tag: String, message: String) {
    Log.e(tag, message)
    CrashlyticsLog(tag, message, true)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.e instead")
fun Context.JLogE(message: String) {
    JLogE("Error: ${this.javaClass.simpleName}-", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.wtf instead")
fun JLogW(message: String) {
    JLogW("Warning: ", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.wtf instead")
fun JLogW(tag: String, message: String) {
    Log.w(tag, message)
    CrashlyticsLog(tag,message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.wtf instead")
fun Context.JLogW(message: String) {
    JLogI("Warning: ${this.javaClass.simpleName}-", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.d instead")
fun JLogD(message: String, throwable: Throwable) {
    JLogD("Debug", throwable.localizedMessage)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.d instead")
fun JLogD(message: String) {
    JLogV("Debug: ", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.d instead")
fun Context.JLogD(message: String) {
    JLogI("Debug: ${this.javaClass.simpleName}-", message)
}

@Deprecated("use com.nativedevps.support.utility.debugging.Log.d instead")
fun JLogD(tag: String, message: String) {
    Log.d(tag, message)
    CrashlyticsLog(tag,message)
}

private fun CrashlyticsLog(
    tag: String,
    message: String,
    force:Boolean = false
) {
    if (debugMode || force) {
        FirebaseCrashlytics.getInstance().log("${tag}: ${message}")
    }
}