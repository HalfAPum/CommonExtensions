package com.halfapum.general.coroutines.exception

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.halfapum.general.coroutines.launch
import com.halfapum.general.coroutines.launchCatching
import com.halfapum.general.coroutines.subscribeFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.SharedFlow

/**
 * Singleton.
 *
 * Holds [CoroutineExceptionHandler] for all [launchCatching].
 *
 * If you are going to use [launchCatching] with different handler
 * just change this var to own handler inside your [Application] class.
 */
var generalCoroutineExceptionHandler: CoroutineExceptionHandler = DefaultCoroutineExceptionHandler()


fun Activity.collectLatestException(block: suspend (item: Throwable) -> Unit) {
    (this as? LifecycleOwner)?.subscribeFlow(ExceptionPropagator.exceptionFlow, block)
}

fun Activity.collectExceptions(block: suspend (item: Throwable) -> Unit) = (this as? LifecycleOwner)?.apply {
    launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            ExceptionPropagator.exceptionFlow.collect(block)
        }
    }
}