package com.example.weatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// creating instance of RetroFit to use it's methods
//
object RetrofitInstance {

    private const val BASE_URL = "https://api.weatherapi.com"


    val retrofit: Retrofit by lazy {  //initialized lazily - created only when accessed for first time
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL
            .addConverterFactory(GsonConverterFactory.create()) // Add GSON for JSON parsing
            .build()

    }
    //variable to integrate api with retrofit - define endpoints and make network calls
    val apiService = retrofit.create(ApiService::class.java)
    // this apiService instance is used to call all the methods of the retrofit class

// val apiService : ApiService = getInstance().create(ApiService::class.java)
}

    /*
    // better to use function if need multiple instances / apis
    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL
            .addConverterFactory(GsonConverterFactory.create()) // Add GSON for JSON parsing
            .build()
    }
        */
