package com.halfapum.samplecoroutines

import android.app.Application
import com.halfapum.general.coroutines.exception.DefaultCoroutineExceptionHandler
import com.halfapum.general.coroutines.exception.generalCoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //ADVANCED USAGE
        //Change default exception handler to custom
        generalCoroutineExceptionHandler
        //Uncomment next line to check work
        // generalCoroutineExceptionHandler = MyExceptionHandler()
    }
}

class MyExceptionHandler : DefaultCoroutineExceptionHandler() {

    override val coroutineTag = "MY TAG"
    override val exceptionMessage = "MY TAG"

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("DUMMY PRINT EXCEPTION CTACHED IN CUSTOM HANDLER ${exception}")
    }

}