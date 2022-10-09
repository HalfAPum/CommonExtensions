package com.halfapum.general.coroutines

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Allow you avoid triple callback to collect latest values from [Flow].
 */
fun <T> LifecycleOwner.subscribeFlow(
    flow: Flow<T>,
    block: suspend (item: T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest {
                block.invoke(it)
            }
        }
    }
}