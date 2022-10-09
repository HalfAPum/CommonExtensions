package com.halfapum.general.coroutines.exception

interface ExceptionMapper<T> {

    fun map(throwable: Throwable): T

}