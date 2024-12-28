package com.example.weatherapp.network

import com.example.weatherapp.viewmodel.WeatherViewModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

//interface to define the endpoints of the API
// this is creating the API

interface ApiService {

    @GET("/v1/current.json")
    suspend fun getWeather(            //Asynchronous call will take time
        @Query("key") apikey : String,   //Query parameter for the API key
        @Query("q") city: String           //Query parameter for the city name
    ) : Response<WeatherModel>             //Whenever call getWeather, will get Response of Weather model


}