package com.alexandreseneviratne.forecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

/**
 * Created by Alexandre SENEVIRATNE on 6/7/2019.
 */
data class ImperialCurrentWeatherEntry(
    @ColumnInfo(name = "condition_icon")
    override val conditionUrlIcon: String,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "feelslikeF")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "precipIn")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "tempF")
    override val temperature: Double,
    @ColumnInfo(name = "visMiles")
    override val visibilityDistance: Double,
    @ColumnInfo(name = "windMph")
    override val windSpeed: Double
) : UnitSpecificCurrentWeatherEntry {
}