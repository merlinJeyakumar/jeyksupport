package com.nativedevps.support.utility.text

const val ENCODING_UTF_8 = "UTF-8"

fun isVersionCompatible(
    currentVersion: String,
    requiredVersion: String
): Boolean {
    val currentParts = currentVersion.split(".").map { it.toInt() }
    val requiredParts = requiredVersion.split(".").map { it.toInt() }

    for (i in 0..2) {
        if (currentParts[i] > requiredParts[i]) return true
        if (currentParts[i] < requiredParts[i]) return false
    }

    return true // If all parts are equal
}