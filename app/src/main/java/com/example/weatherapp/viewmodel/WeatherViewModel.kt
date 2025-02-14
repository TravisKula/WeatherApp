package com.example.weatherapp.viewmodel

import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.Constant
import com.example.weatherapp.network.NetworkResponse
import com.example.weatherapp.network.RetrofitInstance
import com.example.weatherapp.network.WeatherModel
import com.example.weatherapp.weatherui.WeatherScreen
import kotlinx.coroutines.launch

// Fetches weather data from API using Retrofit

class WeatherViewModel : ViewModel() {

    // Instance of api to use in viewModel
    private val apiService = RetrofitInstance.apiService // Instance to call the weather API
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>() // Holds the API response
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult // Public variable that UI can observe

    fun getWeatherData(city: String) { // Fetches API data based on City name

        _weatherResult.value = NetworkResponse.Loading // Response when loading (data being fetched)

        viewModelScope.launch { // Wrap in coroutine b/c will take some time
            // response from the api call
            try {
                val response = apiService.getWeather(Constant.apiKey, city) // Passes API key to apiService object
                if(response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it) // Response when successful
                    }

                    //  Log.i("Response : ",response.body().toString())  //check before applying logic
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load")   //response when error
                    //  Log.i("Error : ",response.message())

                }
            }
            catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load")  //response when error


            }
        }
    }
}


// https://api.weatherapi.com/v1/current.json?key=c6f78186497e4b34ba3164901242612&q=London&aqi=no
// c6f78186497e4b34ba3164901242612