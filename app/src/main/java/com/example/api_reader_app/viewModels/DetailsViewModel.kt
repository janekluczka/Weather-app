package com.example.api_reader_app.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api_reader_app.database.DailyForecast
import com.example.api_reader_app.repositories.ForecastRepositoryImpl
import kotlinx.coroutines.*

class DetailsViewModel(
    private val dayID: Long,
    private val repository: ForecastRepositoryImpl
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob)

    private val _dayTemperature = MutableLiveData<String>()
    val dayTemperature: LiveData<String>
        get() = _dayTemperature

    private val _nightTemperature = MutableLiveData<String>()
    val nightTemperature: LiveData<String>
        get() = _nightTemperature

    var forecast = MutableLiveData<DailyForecast?>()

    init {
        Log.i("DetailsViewModel", "DetailsViewModel created")

        getDayForecast()
    }

    private fun getDayForecast() {
        Log.i("DetailsViewModel", "getDayForecast called")

        uiScope.launch {
            forecast.postValue(getFromDatabase())
        }
    }

    private suspend fun getFromDatabase(): DailyForecast? {
        Log.i("DetailsViewModel", "getFromDatabase called")

        return withContext(Dispatchers.IO) {
            repository.getForecastByDay(dayID)
        }
    }

    fun setNewTemperatures() {
        Log.i("DetailsViewModel", "setNewTemperatures called")

        _dayTemperature.value = forecast.value?.dayTemperature.toString() + "°"
        _nightTemperature.value = forecast.value?.nightTemperature.toString() + "°"
    }

}