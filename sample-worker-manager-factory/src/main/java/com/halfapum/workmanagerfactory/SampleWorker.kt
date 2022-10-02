package com.halfapum.workmanagerfactory

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.halfapum.general.workmanager.ChildWorkerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

class SampleWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val sampleDependency: SampleDependency,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        sampleDependency.sampleField
        delay(3000L)

        return Result.success()
    }

    @AssistedFactory
    interface Factory : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): SampleWorker
    }
}