package com.nativedevps.support.base_class

import com.nativedevps.support.coroutines.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

//P - request parameter
//T - response
@Deprecated("Prefer using #FlowUseCase insteadof")
abstract class UseCase<in P, T>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): NetworkResult<T> {
        return withContext(coroutineDispatcher) {
            performAction(parameters)
        }
    }

    protected abstract suspend fun performAction(parameters: P): NetworkResult<T>

}