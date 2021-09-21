package com.example.api_reader_app

import android.app.Application
import android.util.Log
import com.example.api_reader_app.koin.databaseModule
import com.example.api_reader_app.koin.detailsViewModelModule
import com.example.api_reader_app.koin.listViewModelModule
import com.example.api_reader_app.koin.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.i("MainApplication", "onCreate called")

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    listViewModelModule,
                    detailsViewModelModule
                )
            )
        }
    }

}