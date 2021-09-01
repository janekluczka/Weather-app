package com.example.api_reader_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_reader_app.database.ForecastDatabaseDao

class DetailsViewModelFactory (
        private val dayID: Long,
        private val dataSource: ForecastDatabaseDao,
    ) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(dayID, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        }

}
