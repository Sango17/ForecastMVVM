package com.alexandreseneviratne.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.alexandreseneviratne.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

/**
 * Created by Alexandre SENEVIRATNE on 6/9/2019.
 */
interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}