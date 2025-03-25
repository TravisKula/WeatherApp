package com.example.weatherapp.weatherui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.network.ForecastDay
import com.example.weatherapp.network.ForecastModel
import com.example.weatherapp.network.NetworkResponse
import com.example.weatherapp.network.WeatherModel
import com.example.weatherapp.ui.theme.DeepNightBlue
import com.example.weatherapp.ui.theme.SoftBlue
import com.example.weatherapp.ui.theme.SoftLavender
import com.example.weatherapp.ui.theme.SoftSkyBlye
import com.example.weatherapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = hiltViewModel(), modifier: Modifier) {

    var city by remember { mutableStateOf("") }
    var weatherResult = weatherViewModel.weatherResult.observeAsState()
    val forecastResult by weatherViewModel.forecastResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current // Hides keyboard
    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftLavender,
            SoftBlue,
            SoftSkyBlye,
            SoftLavender
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(start = 26.dp, top = 26.dp, end = 26.dp, bottom = 8.dp),

                value = city,
                onValueChange = { city = it },
                label = {
                    Text(
                        text = "Search for a City",
                        style = MaterialTheme.typography.labelLarge

                    )
                },
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
                ),
                shape = RoundedCornerShape(28.dp),

                trailingIcon = {
                    IconButton(
                        onClick = {
                            weatherViewModel.getWeatherData(city)
                            weatherViewModel.getForecastData(city)
                            keyboardController?.hide() // Hides keyboard when click search
                        }
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search for City",
                            modifier = Modifier.size(28.dp),
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedContainerColor = MaterialTheme.colorScheme.surface, // Interior background color
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface, // Background when not focused

                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                ),

                maxLines = 1,
                singleLine = true
            )


            if (weatherResult.value is NetworkResponse.Loading) {
                Spacer(modifier = Modifier.height(48.dp))
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            } else {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(corner = CornerSize(44.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = SoftBlue)

                ) {
                    // Handles different states of weather results
                    when (val resultWeather =
                        weatherResult.value) {
                        is NetworkResponse.Error -> {

                            Text(
                                text = resultWeather.message,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                        }

                        is NetworkResponse.Success -> {
                            WeatherDetails(data = resultWeather.data)

                            Spacer(Modifier.height(0.dp))

                            WeatherGridDisplay(data = resultWeather.data)
                        }

                        null -> {}
                        else -> {}
                    }
                }

            }

            // Forecast Data Handling
            when (val resultForecast =
                forecastResult) {

                is NetworkResponse.Error -> {

                }

                NetworkResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResponse.Success -> {
                    WeatherForecastList(forecastData = resultForecast.data)

                }

                null -> {}

            }
        }
    }

}

// Details in Main Card (City, icon, temp, feels like
@Composable
fun WeatherDetails(data: WeatherModel) {
    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier.size(400.dp)
    )
    {

        // Name of City Display
        Text(
            modifier = Modifier

                .align(Alignment.TopCenter)
                .offset(x = 10.dp, y = 0.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = data.location.name.uppercase(),
            fontStyle = FontStyle.Normal,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.goblin_one_regular)),
                fontSize = 32.sp,
                color = Color.White
            ),
            maxLines = 1, // Ensures text stays on 1 line
            overflow = TextOverflow.Visible, // Text will shrink to fit 1 line
            softWrap = false
        )
        Spacer(modifier = Modifier.height(2.dp))

        AsyncImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (30).dp, y = 40.dp)
                .size(250.dp),
            model = "https:${data.current.condition.icon}".replace(
                "64x64",
                "128x128"
            ),
            contentDescription = null
        )

        Text(
            text = " ${
                data.current.temp_c.toDoubleOrNull()?.roundToInt() ?: "N/A"
            }° ",
            fontSize = 150.sp,
            color = Color.Blue.copy(alpha = 0.90f), // 90% transparency

            modifier = Modifier
                .align(Alignment.BottomCenter) // Adjust position
                .offset(x = -60.dp, y = 0.dp) // Move slightly over icon
        )
        Text(
            text = "Feels Like: ${data.current.feelslike_c} °C",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = (80).dp, y = 0.dp)
        )
    }
}

// Card for Humidity, HeatIndex, Wind
@Composable
fun WeatherGridDisplay(data: WeatherModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(28.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = SoftSkyBlye)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            WeatherDetailsItem(Icons.Default.WaterDrop, "Humidity", data.current.humidity + "%")
            VerticalDivider(
                color = Color.White, // White vertical line
                modifier = Modifier
                    .height(40.dp) // Adjusts height to match content
                    .width(1.dp) // Creates a thin line
            )

            WeatherDetailsItem(
                Icons.Default.Thermostat,
                "Heat Index",
                data.current.heatindex_c + "°C"
            )
            VerticalDivider(
                color = Color.White,
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp)
            )

            WeatherDetailsItem(Icons.Default.Air, "Wind", data.current.wind_kph + " km/h")

        }
    }
}

@Composable
fun WeatherDetailsItem(icon: ImageVector, key: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = key,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )

        Text(
            text = key,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

// LazyRow to show the most recent searches
@Composable
fun WeatherForecastList(forecastData: ForecastModel) {
    Text(
        text = "3 Day Forecast",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        fontSize = 20.sp,
        color = Color.Blue,
        textAlign = TextAlign.Center
    )

    LazyRow(
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),

        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(forecastData.forecast.forecastday) { day ->
            WeatherForecast(day) // Pass the ForecastDay object
        }
    }
}

// WeatherForecast Details (Date, Avg Temp, Humidity, Wind)
@Composable
fun WeatherForecast(day: ForecastDay) {
    Card(
        modifier = Modifier
            .height(180.dp)
            .width(170.dp),
        colors = CardDefaults.cardColors(containerColor = DeepNightBlue),

        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Displays weather condition icon
            AsyncImage(
                model = "https:${day.day.condition.icon}",
                contentDescription = day.day.condition.text,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = formatDate(day.date),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Avg Temp: ${day.day.avgTempC}°C",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Humidity: ${day.day.avgHumidity}%",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Wind: ${day.day.maxWindKph} kph",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

// Function to format date
fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    val parsedDate = inputFormat.parse(date) ?: return date
    return outputFormat.format(parsedDate)
}














