package com.halfapum.samplecoroutines

import com.halfapum.general.coroutines.exception.ExceptionMapper
import java.io.IOException
import java.net.HttpRetryException

//Example exception wrapper
sealed class CustomExceptionWrapper {
    class ServerException: CustomExceptionWrapper()
    class DatabaseException: CustomExceptionWrapper()
    class RepositoryException: CustomExceptionWrapper()
    class UnknownException: CustomExceptionWrapper()
}

object CustomExceptionMapper: ExceptionMapper<CustomExceptionWrapper> {

    //Some exception mappingLogic
    override fun map(throwable: Throwable) = when(throwable) {
        is HttpRetryException -> CustomExceptionWrapper.ServerException()
        is IOException -> CustomExceptionWrapper.DatabaseException()
        is IllegalAccessError -> CustomExceptionWrapper.RepositoryException()
        else -> CustomExceptionWrapper.UnknownException()
    }

}