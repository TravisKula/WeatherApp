package com.example.weatherapp.network

import com.example.weatherapp.viewmodel.WeatherViewModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

//interface to define the endpoints of the API
// this is creating the API

interface WeatherApiService {

    // Current Weather API
    @GET("current.json")
    suspend fun getWeather(            //Asynchronous call will take time
        @Query("key") apikey : String,   //Query parameter for the API key
        @Query("q") city: String           //Query parameter for the city name
    ) : Response<WeatherModel>             //Whenever call getWeather, will get Response of Weather model

    // Forecast Weather API (3-day forecast)
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int = 3  // Fetch 3 days by default
    ): Response<ForecastModel>


}