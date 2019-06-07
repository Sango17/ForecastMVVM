package com.alexandreseneviratne.forecastmvvm.data.network.response

import com.alexandreseneviratne.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.alexandreseneviratne.forecastmvvm.data.db.entity.Location
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)