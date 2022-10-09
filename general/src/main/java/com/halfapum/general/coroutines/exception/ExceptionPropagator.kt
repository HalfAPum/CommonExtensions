package com.halfapum.general.coroutines.exception

import android.app.Application
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object ExceptionPropagator {

    private const val DEFAULT_EXCEPTION_FLOW_REPLAY = 10
    private const val DEFAULT_EXCEPTION_FLOW_BUFFER = 10
    private val DEFAULT_EXCEPTION_FLOW_STRATEGY = BufferOverflow.DROP_LATEST


    private var _exceptionFlow: MutableSharedFlow<Throwable> = MutableSharedFlow(
        DEFAULT_EXCEPTION_FLOW_REPLAY,
        DEFAULT_EXCEPTION_FLOW_BUFFER,
        DEFAULT_EXCEPTION_FLOW_STRATEGY,
    )

    var exceptionFlow = _exceptionFlow.asSharedFlow()
        private set

    /**
     * Configure [MutableSharedFlow] strategy if default doesn't correspond your requirements.
     */
    fun Application.configureExceptionFlow(
        replay: Int = DEFAULT_EXCEPTION_FLOW_REPLAY,
        extraBufferCapacity: Int = DEFAULT_EXCEPTION_FLOW_BUFFER,
        onBufferOverflow: BufferOverflow = DEFAULT_EXCEPTION_FLOW_STRATEGY
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