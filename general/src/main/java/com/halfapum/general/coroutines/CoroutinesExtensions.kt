package com.halfapum.general.coroutines

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit)
: Job = viewModelScope.launch(context, start, block)

fun LifecycleOwner.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit)
: Job = lifecycleScope.launch(context, start, block)

fun View.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit)
: Job? = (context as? LifecycleOwner)?.launch(context, start, block)


/**
 * Catches exception and logs it with default android [Log]
 *
 * Open for override if default logic is not enough
 * or if you want to override it.
 */
open class DefaultCoroutineExceptionHandler : AbstractCoroutineContextElement(CoroutineExceptionHandler),
    CoroutineExceptionHandler {

    open val coroutineTag: String = COROUTINE_TAG
    open val exceptionMessage: String = COROUTINE_EXCEPTION_MESSAGE

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.e(coroutineTag, exceptionMessage, exception)
    }

    companion object {
        private const val COROUTINE_TAG = "COROUTINE_TAG"
        private const val COROUTINE_EXCEPTION_MESSAGE = "COROUTINE_EXCEPTION_MESSAGE"
    }
}

/**
 * Singleton.
 *
 * Holds [CoroutineExceptionHandler] for all [launchCatching].
 *
 * If you are going to use [launchCatching] with different handler
 * just change this var to own handler inside your [Application] class.
 */
var generalCoroutineExceptionHandler: CoroutineExceptionHandler = DefaultCoroutineExceptionHandler()

/**
 * Use [launchCatching] only when you call root coroutine
 * otherwise it has no effect.
 * For more about exception handling read official docs:
 * https://kotlinlang.org/docs/exception-handling.html
 */
fun ViewModel.launchCatching(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit)
: Job = viewModelScope.launch(context + generalCoroutineExceptionHandler, start, block)

fun LifecycleOwner.launchCatching(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit)
: Job = lifecycleScope.launch(context + generalCoroutineExceptionHandler, start, block)

fun View.launchCatching(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit)
: Job? = (context as? LifecycleOwner)?.launchCatching(context + generalCoroutineExceptionHandler, start, block)