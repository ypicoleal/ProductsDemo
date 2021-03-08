package com.example.productsdemo

import android.app.Application
import com.example.productsdemo.di.actionsModule
import com.example.productsdemo.di.networkModule
import com.example.productsdemo.di.repositoriesModule
import com.example.productsdemo.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(listOf(
                repositoriesModule,
                actionsModule,
                networkModule,
                viewModelsModule
            ))
        }
    }
}