package com.example.weatherapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interface defining API endpoints for fetching weather data
interface WeatherApiService {

    // Fetches current weather data
    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apikey: String, // API key as a query parameter
        @Query("q") city: String // City name as a query parameter
    ): Response<WeatherModel>

    // Fetches 3-day weather forecast
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int = 3  // Fetches 3 days by default
    ): Response<ForecastModel>

}

