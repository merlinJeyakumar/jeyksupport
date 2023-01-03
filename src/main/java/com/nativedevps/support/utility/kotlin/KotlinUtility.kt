package com.nativedevps.support.utility.kotlin

suspend fun forInfinite(callback: suspend () -> Boolean) {
    while (true) {
        if (callback()) {
            break
        }
    }
}