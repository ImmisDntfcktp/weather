package com.example.android.weather.ui.main

import androidx.lifecycle.ViewModel
import com.example.android.weather.WeatherApi
import kotlinx.coroutines.*
import java.util.*


class MainViewModel() : ViewModel() {
    val idCity = "550280"

   suspend fun fetchWeatherData(weatherApi: WeatherApi?)= withContext(Dispatchers.IO) {
        weatherApi?.let {
            return@withContext weatherApi.getData()
        }

    }
}
