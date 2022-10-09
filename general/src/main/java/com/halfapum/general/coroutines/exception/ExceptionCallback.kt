package com.halfapum.general.coroutines.exception

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Special typealias for exception callback.
 *
 * Returns boolean to indicate that [Throwable] has been handled or not.
 */
typealias ExceptionCallback = (Throwable) -> Boolean

class CancellableExceptionCallback (
    private val exceptionCallback: ExceptionCallback,
) : Cancellable {

    override fun cancel() {
        ExceptionPropagator.removeExceptionCallback(exceptionCallback)
    }

}

internal class LifecycleCancellableExceptionCallback (
    private val lifecycle: Lifecycle,
    private val exceptionCallback: ExceptionCallback,
) : LifecycleEventObserver, Cancellable {

    private var currentCancellable: Cancellable? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(
        source: LifecycleOwner,
        event: Lifecycle.Event
    ) {
        when(event) {
            Lifecycle.Event.ON_START -> {
                currentCancellable = addCancellableExceptionCallback(exceptionCallback)
            }
            Lifecycle.Event.ON_STOP -> {
                currentCancellable?.cancel()
            }
            Lifecycle.Event.ON_DESTROY -> {
                cancel()
            }
            else -> {}
        }
    }

    override fun cancel() {
        lifecycle.removeObserver(this)

        currentCancellable?.cancel()
        currentCancellable = null
    }

}

interface Cancellable {

    fun cancel()

}