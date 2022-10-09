package com.halfapum.general.coroutines.exception

/**
 * Interface for mapping [Throwable] to custom exception class.
 */
interface ExceptionMapper<T> {

    fun map(throwable: Throwable): T

}