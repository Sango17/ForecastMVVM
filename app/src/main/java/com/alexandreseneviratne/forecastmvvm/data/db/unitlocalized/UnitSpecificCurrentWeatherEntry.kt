package com.alexandreseneviratne.forecastmvvm.data.db.unitlocalized

/**
 * Created by Alexandre SENEVIRATNE on 6/7/2019.
 */

interface UnitSpecificCurrentWeatherEntry {
    val conditionUrlIcon: String
    val conditionText: String
    val feelsLikeTemperature: Double
    val precipitationVolume: Double
    val temperature: Double
    val visibilityDistance: Double
    val windSpeed: Double
}