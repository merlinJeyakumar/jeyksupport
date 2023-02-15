package com.nativedevps.support.utility.event

import com.nativedevps.support.utility.threading.runOnSameThread
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*

object EventBus {
    private val _events = MutableSharedFlow<Any>()
    val events = _events.asSharedFlow()

    fun publish(event: Any) {
        runOnSameThread {
            _events.emit(event)
        }
    }

    suspend inline fun <reified T> subscribe(crossinline onEvent: suspend (T) -> Unit) {
        events.filterIsInstance<T>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }

    inline fun <reified T> subscribe(): Flow<T> {
        return channelFlow<T> {
            subscribe<T> {
                this.send(it)
            }
        }
    }
}