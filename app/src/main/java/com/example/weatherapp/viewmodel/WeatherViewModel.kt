package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.ForecastModel
import com.example.weatherapp.network.NetworkResponse
import com.example.weatherapp.network.WeatherModel
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    // LiveData to hold the current weather data
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    // LiveData to hold the weather forecast data
    private val _forecastResult = MutableLiveData<NetworkResponse<ForecastModel>>()
    val forecastResult: LiveData<NetworkResponse<ForecastModel>> = _forecastResult

    /**
     * Fetches weather data for a given city and updates LiveData.
     */
    fun getWeatherData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            _weatherResult.value = weatherRepository.getWeather(city) // Calls repository
        }
    }

    /**
     * Fetches forecast data for a given city and updates LiveData.
     */
    fun getForecastData(city: String) {
        _forecastResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            _forecastResult.value = weatherRepository.getForecast(city)
        }
    }
}

