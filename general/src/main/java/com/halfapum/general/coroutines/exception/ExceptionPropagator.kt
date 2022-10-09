package com.halfapum.general.coroutines.exception

import android.app.Application
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object ExceptionPropagator {

    private var _exceptionFlow: MutableSharedFlow<Throwable> = MutableSharedFlow(
        10,
        10,
        BufferOverflow.DROP_LATEST,
    )

    var exceptionFlow = _exceptionFlow.asSharedFlow()
        private set

    /**
     * Configure [MutableSharedFlow] strategy if default doesn't correspond your requirements.
     */
    fun Application.configureExceptionFlow(
        replay: Int = 10,
        extraBufferCapacity: Int = 10,
        onBufferOverflow: BufferOverflow = BufferOverflow.DROP_LATEST
    ) {
        _exceptionFlow = MutableSharedFlow(replay, extraBufferCapacity, onBufferOverflow)
        exceptionFlow = _exceptionFlow.asSharedFlow()
    }


    /**
     * Propagate [Throwable] to exception flow.
     */
    fun propagate(throwable: Throwable) {
        _exceptionFlow.tryEmit(throwable)
    }

}