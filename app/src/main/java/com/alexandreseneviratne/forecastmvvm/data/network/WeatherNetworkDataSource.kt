package com.alexandreseneviratne.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.alexandreseneviratne.forecastmvvm.data.network.response.CurrentWeatherResponse

/**
 * Created by Alexandre SENEVIRATNE on 6/7/2019.
 */
interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}