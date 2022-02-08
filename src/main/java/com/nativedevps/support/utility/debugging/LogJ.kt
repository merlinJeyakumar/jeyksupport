package com.nativedevps.support.utility.debugging

import android.content.Context
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

fun Context.JLogI(message: String) {
    JLogI("Info: ${this.javaClass.simpleName}-", message)
}

fun JLogI(message: String) {
    JLogI("Info: ", message)
}

fun JLogI(tag: String, message: String) {
    Log.i(tag, message)
    CrashlyticsLog(tag,message)
}

fun JLogI(message: String, throwable: Throwable) {
    JLogI("Info", throwable.localizedMessage)
}

fun Context.JLogV(message: String) {
    JLogV("Verbose: ${this.javaClass.simpleName}-", message)
}

fun JLogV(message: String, throwable: Throwable) {
    JLogV("Verbose", throwable.localizedMessage)
}

fun JLogV(message: String) {
    JLogV("Verbose: ", message)
}

fun JLogV(tag: String, message: String) {
    Log.v(tag, message)
    CrashlyticsLog(tag,message)
}

fun JLogE(message: String, throwable: Throwable) {
    JLogE("Error", throwable.localizedMessage)
}

fun JLogE(message: String) {
    JLogV("Error: ", message)
}

fun JLogE(tag: String, message: String) {
    Log.e(tag, message)
    CrashlyticsLog(tag, message, true)
}

fun Context.JLogE(message: String) {
    JLogI("Error: ${this.javaClass.simpleName}-", message)
}

fun JLogW(message: String) {
    JLogW("Warning: ", message)
}

fun JLogW(tag: String, message: String) {
    Log.w(tag, message)
    CrashlyticsLog(tag,message)
}

fun Context.JLogW(message: String) {
    JLogI("Warning: ${this.javaClass.simpleName}-", message)
}

fun JLogD(message: String, throwable: Throwable) {
    JLogD("Debug", throwable.localizedMessage)
}

fun JLogD(message: String) {
    JLogV("Debug: ", message)
}

fun Context.JLogD(message: String) {
    JLogI("Debug: ${this.javaClass.simpleName}-", message)
}

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