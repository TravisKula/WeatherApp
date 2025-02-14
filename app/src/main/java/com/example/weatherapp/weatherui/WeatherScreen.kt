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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TextInputService
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.media3.common.util.Log
//import coil3.compose.AsyncImage
import coil3.compose.AsyncImage

import com.example.weatherapp.R
import com.example.weatherapp.network.Condition
import com.example.weatherapp.network.Current
import com.example.weatherapp.network.Location
import com.example.weatherapp.network.NetworkResponse
import com.example.weatherapp.network.WeatherModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, modifier: Modifier) {

    var city by remember { mutableStateOf("") }
    var weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current   // Variable to hide keyboard
    val gradient = Brush.verticalGradient(
           colors = listOf(
               MaterialTheme.colorScheme.primary,
               MaterialTheme.colorScheme.secondary
           )
        // colors = listOf(Color(0xFF2196F3), Color(0xFF6EC6FF) - lighter - hardcoded
        // colors = listOf(Color(0xFF0288D1), Color(0XFF03A9F4) - bit darker
        )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)

    ) {

        Column(
            //  verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(6.dp)


        ) {

            Spacer(Modifier.height(88.dp))

            Text(
                text = "Wacky Weather",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 36.sp
            )


            Spacer(Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically

            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f), // Takes as much space as possible proportional to weight
                    value = city,
                    onValueChange = { city = it },
                    label = {
                        Text(
                            text = "Enter City",
                            fontSize = 22.sp

                        )
                    },
                    textStyle = TextStyle(fontSize = 18.sp)
                )

                IconButton(
                    onClick = {
                        viewModel.getWeatherData(city)
                        keyboardController?.hide()          //Question mark because is nullable
                    },                           //Hides keyboard when click search
                )

                {
                    Icon(
                        imageVector = Icons.Default.Search,   //does not have weight so only takes space of contents
                        contentDescription = "Search for City",
                        modifier = Modifier.size(30.dp),
                    )
                }

            }





            when (val result = weatherResult.value) { //.value represents the current state of the object
                is NetworkResponse.Error -> {

                    Spacer(modifier = Modifier.height(48.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            text = result.message,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                NetworkResponse.Loading -> {

                    Spacer(modifier = Modifier.height(48.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                is NetworkResponse.Success -> {
                    WeatherDetails(data = result.data)
                }

                null -> {}
            }
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
            /*(
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "City Location Icon",
                modifier = Modifier.size(44.dp)
            )
    */
            Text(
                text = data.location.name.uppercase() + ", ",
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = data.location.country,
                fontStyle = FontStyle.Normal,
                //fontSize = 22.sp,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )




        //    Spacer(modifier = Modifier.width(48.dp))
            Spacer(modifier = Modifier.height(60.dp))

        }
        Row {
            Text(
                text = " ${data.current.temp_c} ° c",
                fontSize = 36.sp,
                color = Color.Blue

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
            text = "Current Weather Conditions",
            fontSize = 24.sp

        )

        Spacer(modifier = Modifier.height(14.dp))

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
        elevation = CardDefaults.cardElevation(6.dp),
       // colors = CardDefaults.cardColors(Color.Cyan)
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)

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


/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewWeatherScreen() {
    // Create mock weather data for preview
    val fakeWeatherData = WeatherModel(
        location = Location(name = "New York", country = "USA"),
        current = Current(
            temp_c = "22",
            condition = Condition(icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"),
            humidity = "60",
            heatindex_c = "24",
            feelslike_c = "21",
            wind_kph = "15"
        )
    )

    // Create a fake ViewModel using an anonymous object
    val fakeViewModel = object : WeatherViewModel() {
        override val weatherResult = MutableLiveData<NetworkResponse.Success<WeatherModel>>(
            NetworkResponse.Success(fakeWeatherData)
        )
    }

    // Call the main Composable with mock data
    WeatherScreen(viewModel = fakeViewModel, modifier = Modifier.fillMaxSize())
}

*/



