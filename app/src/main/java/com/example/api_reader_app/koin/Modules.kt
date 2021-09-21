package com.example.api_reader_app.koin

import android.app.Application
import com.example.api_reader_app.database.ForecastDao
import com.example.api_reader_app.database.ForecastDatabase
import com.example.api_reader_app.repositories.ForecastRepositoryImpl
import com.example.api_reader_app.viewmodels.DetailsViewModel
import com.example.api_reader_app.viewmodels.ListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {

    fun provideDatabase(application: Application): ForecastDao {
        return ForecastDatabase.getInstance(application).forecastDao
    }

    single { provideDatabase(androidApplication()) }

}

val repositoryModule = module {

    fun provideForecastRepository(forecastDao: ForecastDao): ForecastRepositoryImpl {
        return ForecastRepositoryImpl(forecastDao)
    }

    single { provideForecastRepository(get()) }

}

val listViewModelModule = module {

    viewModel {
        ListViewModel(get())
    }

}

val detailsViewModelModule = module {

    viewModel {
        DetailsViewModel(get(), get())
    }

}