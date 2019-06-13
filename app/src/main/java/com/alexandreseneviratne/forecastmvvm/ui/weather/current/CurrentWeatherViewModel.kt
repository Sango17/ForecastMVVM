package com.alexandreseneviratne.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.alexandreseneviratne.forecastmvvm.data.repository.ForecastRepository
import com.alexandreseneviratne.forecastmvvm.internal.UnitSystem
import com.alexandreseneviratne.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
