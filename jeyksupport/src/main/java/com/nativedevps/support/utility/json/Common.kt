package com.nativedevps.support.utility.json

import com.google.gson.Gson

fun Any.toJson(): String? {
    try {
        return Gson().toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}