package com.halfapum.workmanagerfactory

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.halfapum.general.workmanager.WorkerFactoryImpl
import javax.inject.Inject

class SampleApplication : Application() {

    @Inject
    lateinit var workerFactory: WorkerFactoryImpl

    private val appComponent by lazy {
        AppComponent.create(this)
    }

    private val workManagerConfiguration by lazy {
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        WorkManager.initialize(this, workManagerConfiguration)
    }
}