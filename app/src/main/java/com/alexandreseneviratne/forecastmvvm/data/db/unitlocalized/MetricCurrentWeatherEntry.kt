package com.alexandreseneviratne.forecastmvvm.data.db.unitlocalized

import androidx.room.ColumnInfo

/**
 * Created by Alexandre SENEVIRATNE on 6/7/2019.
 */
data class MetricCurrentWeatherEntry(
    @ColumnInfo(name = "condition_icon")
    override val conditionUrlIcon: String,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "precipMm")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "tempC")
    override val temperature: Double,
    @ColumnInfo(name = "visKm")
    override val visibilityDistance: Double,
    @ColumnInfo(name = "windKph")
    override val windSpeed: Double
) : UnitSpecificCurrentWeatherEntry {
}