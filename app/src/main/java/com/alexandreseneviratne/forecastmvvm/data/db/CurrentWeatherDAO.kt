package com.alexandreseneviratne.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexandreseneviratne.forecastmvvm.data.db.entity.CURRENT_WEATHER_ID
import com.alexandreseneviratne.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.alexandreseneviratne.forecastmvvm.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.alexandreseneviratne.forecastmvvm.data.db.unitlocalized.MetricCurrentWeatherEntry

/**
 * Created by Alexandre SENEVIRATNE on 6/7/2019.
 */

@Dao
interface CurrentWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather WHERE id == $CURRENT_WEATHER_ID")
    fun getCurrentWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather WHERE id == $CURRENT_WEATHER_ID")
    fun getcurrentWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}