package com.halfapum.general.coroutines.exception

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.halfapum.general.coroutines.launchCatching
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Singleton.
 *
 * Holds [CoroutineExceptionHandler] for all [launchCatching].
 *
 * If you are going to use [launchCatching] with different handler
 * just change this var to own handler inside your [Application] class.
 */
var generalCoroutineExceptionHandler: CoroutineExceptionHandler = DefaultCoroutineExceptionHandler()

fun <T> LifecycleOwner.collectLatestMappedException(
    exceptionMapper: ExceptionMapper<T>,
    block: (item: T) -> Boolean
) {
    collectLatestException {
        val mappedException = exceptionMapper.map(it)
        block(mappedException)
    }
}

fun LifecycleOwner.collectLatestException(exceptionCallback: ExceptionCallback) {
    addLifecycleCancellableExceptionCallback(exceptionCallback)
}

fun addCancellableExceptionCallback(exceptionCallback: ExceptionCallback): CancellableExceptionCallback {
    ExceptionPropagator.addExceptionCallback(exceptionCallback)
    return CancellableExceptionCallback(exceptionCallback)
}

fun LifecycleOwner.addLifecycleCancellableExceptionCallback(exceptionCallback: ExceptionCallback) {
    LifecycleCancellableExceptionCallback(lifecycle, exceptionCallback)
}