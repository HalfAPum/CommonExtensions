package com.halfapum.general

/**
 * Generic wrapper type for data unit transmission
 *
 * Generally, a good practice to make your own error types with sealed classes
 * and not throw [Throwable] error through data streams of Kotlin Coroutines or RxJava
 *
 * @param D data type
 * @param E error type (recommended to use with domain error types)
 */
sealed interface Reaction<out D, out E> {

    interface Success<D>: Reaction<D, Nothing> {

        val data: D
    }

    interface Fail<E> : Reaction<Nothing, E> {

        val error: E
    }

    companion object {

        fun <D> success(data: D): Success<D> = SuccessImpl(data)

        fun <E> fail(error: E): Fail<E> = FailImpl(error)
    }
}

internal data class SuccessImpl<D>(override val data: D) : Reaction.Success<D>

internal data class FailImpl<E>(override val error: E) : Reaction.Fail<E>