package com.nativedevps.support.utility.threading

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.runOnLifeCycle(lifecycleCoroutineScope: LifecycleOwner, callback: (T) -> Unit):Job {
    return lifecycleCoroutineScope.lifecycleScope.launch {
        collect{
            callback(it)
        }
    }
}

fun runOnAsyncThread(callback: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.IO).launch {
        callback()
    }
}

fun CoroutineScope.runOnMainThread(callback: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Main).launch {
        callback()
    }
}

private fun executeAndMainCallback(
    backgroundExecution: () -> Unit,
    mainThreadExecution: () -> Unit
): Job {
    return runOnAsyncThread {
        backgroundExecution()
        runOnMainThread {
            mainThreadExecution()
        }
    }
}