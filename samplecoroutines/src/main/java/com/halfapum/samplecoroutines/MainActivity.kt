package com.halfapum.samplecoroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.exception.collectLatestException
import com.halfapum.general.coroutines.exception.collectLatestMappedException
import com.halfapum.general.coroutines.launch
import com.halfapum.general.coroutines.launchCatching
import java.net.HttpRetryException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Subscribe for coroutines exceptions
        collectLatestException {
            println("Some launch coroutine FIRED EXCEPTION: $it")
            true
        }

        //Subscribe for custom coroutines exceptions
        collectLatestMappedException(CustomExceptionMapper) {
            println("CUSTOM EXCEPTION!!!: $it")
            true
        }

        //Or for ease of usage create next extensions and use it like that
        fun LifecycleOwner.collectLatestMappedException(block: (item: CustomExceptionWrapper) -> Boolean) {
            collectLatestMappedException(CustomExceptionMapper, block)
        }

        collectLatestMappedException {
            println("MY EXTENSIONS MAPPED EXCEPTION $it")
            true
        }
        //End of useful extension



        //launch coroutine inside lifecycleOwner
        launch {
            println("launch coroutine inside lifecycleOwner")
        }

        //launch coroutine catching exception inside lifecycleOwner
        launchCatching {
            println("launch coroutine inside lifecycleOwner")
            throw IllegalArgumentException("Some illegal exception")
        }

        //Create mainViewModel
        val mainViewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)
        with(mainViewModel) {
            doSomeWork()
            doSomeWorkCatchingException()
        }

    }
}

class MainViewModel: ViewModel() {

    //launch coroutine inside viewModel
    fun doSomeWork() {
        launch {
            println("launch coroutine inside viewModel")
        }
    }

    //launch coroutine catching exception inside viewModel
    fun doSomeWorkCatchingException() {
        launchCatching {
            println("launch coroutine inside viewModel")
            throw HttpRetryException("Some illegal exception", 11)
        }
    }

}