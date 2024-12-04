package com.nativedevps.support.utility.threading

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectOnLifeCycle(
    lifecycleCoroutineScope: LifecycleOwner,
    callback: ((T) -> Unit)? = null
): Job {
    return lifecycleCoroutineScope.lifecycleScope.launch {
        collectLatest {
            callback?.invoke(it)
        }
    }
}

fun <T> Flow<T>.runOnLifeCycle(
    lifecycleCoroutineScope: LifecycleOwner,
    callback: ((T) -> Unit)? = null
): Job {
    return lifecycleCoroutineScope.lifecycleScope.launch {
        collect {
            callback?.invoke(it)
        }
    }
}

fun runOnSameThread(callback: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Unconfined).launch {
        callback()
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

fun <T> Flow<T>.runOnViewHolder(callback: ((T) -> Unit)? = null): Job {
    return CoroutineScope(Dispatchers.Main).launch {
        collectLatest {
            callback?.invoke(it)
        }
    }
}

fun <T> Flow<T>.collectOnLifeCycle(
    lifecycleCoroutineScope: LifecycleOwner,
    callback: ((T) -> Unit)? = null
): Job {
    return lifecycleCoroutineScope.lifecycleScope.launch {
        collectLatest {
            callback?.invoke(it)
        }
    }
}

fun <T> Flow<T>.repeatEvery(interval: Long) = flow {
    while (true) {
        this@repeatEvery.collect {
            emit(it)
        }
        delay(interval)
    }
}

fun <T> Flow<T>.firstOrNullOnLifecycle(
    lifecycleCoroutineScope: LifecycleOwner,
    callback: ((T?) -> Unit)? = null
): Job {
    return lifecycleCoroutineScope.lifecycleScope.launch {
        callback?.invoke(firstOrNull())
    }
}

fun <T> runAsyncFlow(execution: suspend () -> T) = channelFlow<T> {
    trySend(execution())
}.flowOn(Dispatchers.IO)