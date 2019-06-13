package com.alexandreseneviratne.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.alexandreseneviratne.forecastmvvm.R
import com.alexandreseneviratne.forecastmvvm.data.network.ApixuWeatherApiService
import com.alexandreseneviratne.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.alexandreseneviratne.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.alexandreseneviratne.forecastmvvm.internal.glide.GlideApp
import com.alexandreseneviratne.forecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by kodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            textView_more_info.visibility = View.VISIBLE

            updateLocation("Livry_Gargan")
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updateWind(it.windSpeed)
            updatePrecipition(it.precipitationVolume)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("https:${it.conditionUrlIcon}")
                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like: $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updateWind(windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km/h", "mph")
        textView_wind.text = "Wind: $windSpeed $unitAbbreviation"
    }

    private fun updatePrecipition(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}
