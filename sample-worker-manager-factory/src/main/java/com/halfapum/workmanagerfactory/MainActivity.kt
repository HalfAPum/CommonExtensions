package com.halfapum.workmanagerfactory

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    private val workManager by lazy { WorkManager.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.begin_work).setOnClickListener {
            startWork()
        }
    }

    private fun startWork() {
        val workRequest = OneTimeWorkRequestBuilder<SampleWorker>()
            .build()
        workManager.enqueue(workRequest)
    }
}