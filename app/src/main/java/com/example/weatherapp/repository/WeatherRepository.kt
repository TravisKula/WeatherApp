package com.example.weatherapp.repository


import com.example.weatherapp.network.Constant
import com.example.weatherapp.network.Forecast
import com.example.weatherapp.network.ForecastModel
import com.example.weatherapp.network.NetworkResponse
import com.example.weatherapp.network.WeatherApiService
import com.example.weatherapp.network.WeatherModel
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) {

    suspend fun getWeather(city: String): NetworkResponse<WeatherModel> {
        return try {
            val weatherResponse = apiService.getWeather(Constant.apiKey, city)
            if (weatherResponse.isSuccessful) {
                weatherResponse.body()?.let {
                    return NetworkResponse.Success(it) }
            }
            NetworkResponse.Error("-Failed to Load Weather-")
        } catch (e: Exception) {
            NetworkResponse.Error("-Failed to Load Weather-")
        }
    }

    suspend fun getForecast(city: String): NetworkResponse<ForecastModel> {
        return try {
            val forecastResponse = apiService.getForecast(Constant.apiKey, city)
            if (forecastResponse.isSuccessful) {
                forecastResponse.body()?.let {
                    return NetworkResponse.Success(it) }
            }
            NetworkResponse.Error("Failed to load forecast data")
        } catch (e: Exception) {
            NetworkResponse.Error("Failed to load forecast data")
        }
    }


}