package com.halfapum.general.coroutines.exception

import android.util.Log
import com.halfapum.general.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Catches exception and logs it with default android [Log].
 *
 * Open for inheritance if default logic is not enough
 * or if you want to override it.
 */
open class DefaultCoroutineExceptionHandler : AbstractCoroutineContextElement(
    CoroutineExceptionHandler
), CoroutineExceptionHandler {

    open val coroutineTag: String = COROUTINE_TAG
    open val exceptionMessage: String = COROUTINE_EXCEPTION_MESSAGE

    /**
     * If you override this fun don't forget to call [ExceptionPropagator.propagate].
     */
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(coroutineTag, exceptionMessage, exception)
        }

        ExceptionPropagator.propagate(exception)
    }

    companion object {
        private const val COROUTINE_TAG = "COROUTINE_TAG"
        private const val COROUTINE_EXCEPTION_MESSAGE = "COROUTINE_EXCEPTION_MESSAGE"
    }
}
