package com.halfapum.general.coroutines.exception

import java.util.*
import kotlin.collections.ArrayDeque

object ExceptionPropagator {

    private val exceptionCallbacksQueue = ArrayDeque<ExceptionCallback>()

    private val unhandledExceptions = LinkedList<Throwable>()

    fun addExceptionCallback(exceptionCallback: ExceptionCallback) {
        exceptionCallbacksQueue.add(exceptionCallback)

        if (unhandledExceptions.isNotEmpty()) {
            val exceptions = LinkedList(unhandledExceptions)
            unhandledExceptions.clear()

            exceptions.forEach {
                propagate(it)
            }
        }
    }

    fun removeExceptionCallback(exceptionCallback: ExceptionCallback) {
        exceptionCallbacksQueue.remove(exceptionCallback)
    }


    /**
     * Propagate [Throwable] to exception flow.
     */
    fun propagate(throwable: Throwable) {
        //Iterate over ArrayDeque in reversed order.
        for(index in exceptionCallbacksQueue.indices.reversed()) {
            val exceptionHandled = exceptionCallbacksQueue[index].invoke(throwable)

            if (exceptionHandled) return
        }

        //No active handlers.
        //Save exception for future active handlers.
        unhandledExceptions.add(throwable)
    }

}