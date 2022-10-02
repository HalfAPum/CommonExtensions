package com.halfapum.general.workmanager

import androidx.work.ListenableWorker
import dagger.Module
import dagger.multibindings.Multibinds

@Module
@Suppress("unused")
interface WorkerBindingModule {

    @Multibinds
    fun provideWorkersMap(): Map<Class<out ListenableWorker>, ChildWorkerFactory>
}