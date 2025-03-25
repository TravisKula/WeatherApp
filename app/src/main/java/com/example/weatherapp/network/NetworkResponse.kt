package com.example.weatherapp.network

// Defines network response states - successful, loading, error
// T refers to WeatherModel. T is Generic
sealed class NetworkResponse<out T> {

    data class Success<out T>(val data : T): NetworkResponse<T>()
    data class Error(val message : String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()

}