package com.nativedevps.support.utility.network

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.repeatEvery(interval: Long) = flow {
    while (true) {
        this@repeatEvery.collect {
            emit(it)
        }
        delay(interval)
    }
}