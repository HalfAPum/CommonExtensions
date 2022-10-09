package com.halfapum.samplecoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.halfapum.general.coroutines.exception.collectLatestException
import com.halfapum.general.coroutines.launch
import com.halfapum.general.coroutines.launchCatching

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Subscribe for coroutines exceptions
        collectLatestException {
            println("Some launch coroutine FIRED EXCEPTION: $it")
        }



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
            throw IllegalArgumentException("Some illegal exception")
        }
    }

}