package com.nativedevps.support.utility.text

const val ENCODING_UTF_8 = "UTF-8"

fun isVersionUpdated(requiredVersion: String, currentVersion: String): Boolean {
    val required = requiredVersion.split(".").map { it.toInt() }
    val current = currentVersion.split(".").map { it.toInt() }

    for (i in required.indices) {
        if (current.getOrElse(i) { 0 } < required[i]) return false
        if (current.getOrElse(i) { 0 } > required[i]) return true
    }
    return true
}