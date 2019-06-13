package com.alexandreseneviratne.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.alexandreseneviratne.forecastmvvm.data.db.CurrentWeatherDAO
import com.alexandreseneviratne.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.alexandreseneviratne.forecastmvvm.data.network.WeatherNetworkDataSource
import com.alexandreseneviratne.forecastmvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    private fun persistFetchedCurrentWeather(newCurrentWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDAO.upsert(newCurrentWeather.currentWeatherEntry)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) currentWeatherDAO.getCurrentWeatherMetric()
            else currentWeatherDAO.getcurrentWeatherImperial()
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            "Livry_Gargan",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}