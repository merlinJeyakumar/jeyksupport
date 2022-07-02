package com.nativedevps.support.utility.threading

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun runOnNewThread(callback: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.IO).launch {
        callback()
    }
}

fun CoroutineScope.runOnUiThread(callback: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Main).launch {
        callback()
    }
}

fun executeAndMainCallback(
    backgroundExecution: () -> Unit,
    mainThreadExecution: () -> Unit
): Job {
    return runOnNewThread {
        backgroundExecution()
        runOnUiThread {
            mainThreadExecution()
        }
    }
}