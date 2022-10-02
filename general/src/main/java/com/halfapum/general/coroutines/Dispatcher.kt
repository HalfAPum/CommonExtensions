package com.halfapum.general.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext

/**
 * Use this class to avoid HARDCODING dispatchers.
 *
 * Allows inject dispatchers. Makes your code at least testable if mind test it one time.
 */
@OptIn(DelicateCoroutinesApi::class)
data class Dispatcher(
    val Main: CoroutineDispatcher = Dispatchers.Main,
    val IO: CoroutineDispatcher = Dispatchers.IO,
    val Default: CoroutineDispatcher = Dispatchers.Main,
    val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined,
    private val NewThread: CoroutineDispatcher? = null
) {

    fun getNewThread(name: String) = NewThread ?: newSingleThreadContext(name)

}