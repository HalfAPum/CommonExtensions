package com.halfapum.workmanagerfactory

import android.content.Context
import com.halfapum.general.workmanager.ChildWorkerFactory
import com.halfapum.general.workmanager.WorkerBindingModule
import com.halfapum.general.workmanager.WorkerKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        WorkerModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance appCtx: Context,
        ): AppComponent
    }

    fun inject(target: SampleApplication)

    companion object {

        fun create(appCtx: Context): AppComponent {
            return DaggerAppComponent.factory()
                .create(appCtx)
        }
    }
}

@Module
@Suppress("unused")
interface WorkerModule : WorkerBindingModule {

    @[Binds IntoMap WorkerKey(SampleWorker::class)]
    fun bindSampleWorker(factory: SampleWorker.Factory): ChildWorkerFactory
}