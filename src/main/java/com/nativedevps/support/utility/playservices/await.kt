package com.nativedevps.support.utility.playservices

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

public suspend fun <T> Task<T>.await(): T {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException(
                    "Task $this was cancelled normally."
                )
            } else {
                result
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(result)
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}