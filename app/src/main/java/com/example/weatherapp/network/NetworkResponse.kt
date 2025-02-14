package com.example.weatherapp.network

// to define the responses - successful, loading, error
// T refers to WeatherModel. T is Generic
sealed class NetworkResponse<out T> {              //sealed class - value amount given set of options
                                                    //<out T> so can be wrapped with anything
    data class Success<out T>(val data : T): NetworkResponse<T>()
    data class Error(val message : String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}