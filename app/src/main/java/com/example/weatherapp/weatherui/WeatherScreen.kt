package com.example.weatherapp.weatherui


import androidx.collection.intFloatMapOf
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TextInputService
import androidx.compose.ui.text.rememberTextMeasurer

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.media3.common.util.Log
//import coil3.compose.AsyncImage
import coil3.compose.AsyncImage

import com.example.weatherapp.R
import com.example.weatherapp.network.Current
import com.example.weatherapp.network.Location
import com.example.weatherapp.network.NetworkResponse
import com.example.weatherapp.network.WeatherModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, modifier: Modifier) {

    var city by remember { mutableStateOf("") }
    var weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current   //variable to hide keyboard

    Column(
      //  verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(10.dp)

    ) {
        Spacer(Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = {city = it},
                label = {
                    Text(text = "Enter City")
                }

            )

            IconButton(
                onClick =  {viewModel.getWeatherData(city)
                    keyboardController?.hide()          //Question mark because is nullable
                           },                           //Hides keyboard when click search


            )

            {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search for City",
                    modifier = Modifier.size(30.dp),


                )
            }


            }

            when(val result = weatherResult.value){
                is NetworkResponse.Error -> {
                    Text(text = result.message)
                }

                NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }
                is NetworkResponse.Success -> {
                    WeatherDetails(data = result.data )
                }
                null -> {}
            }
        }
    }


@Composable
fun WeatherDetails(data : WeatherModel) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "City Location Icon",
                modifier = Modifier.size(44.dp)
            )

            Text(text = data.location.name+ ",")
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = data.location.country)

            Spacer(modifier = Modifier.width(48.dp))
            Spacer(modifier = Modifier.height(60.dp))

        }
        Row {
            Text(
                text = " ${data.current.temp_c} ° c",
                fontSize = 36.sp

            )
        }
            //   Spacer(modifier = Modifier.height(12.dp))




        AsyncImage(
            modifier = Modifier
                .size(160.dp),
            //  model = "https://cdn.weatherapi.com/weather/64x64/night/248.png",
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = null
        )

        Text(
            text = "Current Weather Condition"
        )



        WeatherGridDisplay(data = data)
        WeatherSnapshotList(data = data)
    }


}



@Composable
fun WeatherGridDisplay(data: WeatherModel) {

    Column(
      //  modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()

        ) {
            WeatherDetailsCard("Humidity", data.current.humidity+ "%")
            WeatherDetailsCard("Heat Index", data.current.heatindex_c+ "°C")
         //   WeatherDetailsCard("Precip", data.current.precip_in+ "mm")

        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()

        ) {
            WeatherDetailsCard("Feels Like", data.current.feelslike_c+ " °C")
            WeatherDetailsCard("Wind", data.current.wind_kph+ " km/h")
        }

    }
}






@Composable
fun WeatherDetailsCard(key: String, value: String) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp),
        //   elevation = 4.dp
    )


    {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(text = key)
            Text(text = value)

        }
    }
}

// Add in Lazy Column to display last 5 searches at bottom (scrollable)

// below is the code for a lazy column to show the most recent searches.
// I will need to implement room db/or Firebase RealtimeDB or DataStore to store (to be completed)
@Composable
fun WeatherSnapshotList(data: WeatherModel) {


    LazyColumn(
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(5, itemContent = {
                WeatherSnapshot(data)
            })

        })
}





@Composable
fun WeatherSnapshot(data: WeatherModel) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .height(76.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Text(text = data.location.name)

            Spacer(modifier = Modifier.width(20.dp))

            Text(text = data.current.temp_c)
        }
    }
}



//WeatherScreen update




