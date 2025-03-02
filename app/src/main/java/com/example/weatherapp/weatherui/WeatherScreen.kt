package com.example.weatherapp.weatherui


import androidx.collection.intFloatMapOf
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
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
import com.example.weatherapp.ui.theme.DeepNightBlue
import com.example.weatherapp.ui.theme.DeepPurple
import com.example.weatherapp.ui.theme.DeepRoyalPurple
import com.example.weatherapp.ui.theme.LightTeal
import com.example.weatherapp.ui.theme.MutedTeal
import com.example.weatherapp.ui.theme.PastelPeach
import com.example.weatherapp.ui.theme.Purple
import com.example.weatherapp.ui.theme.RichPurple
import com.example.weatherapp.ui.theme.SoftBlue
import com.example.weatherapp.ui.theme.SoftCyan
import com.example.weatherapp.ui.theme.SoftLavender
import com.example.weatherapp.ui.theme.Yellow
import com.example.weatherapp.viewmodel.WeatherViewModel
import okio.blackholeSink

import kotlin.io.path.moveTo
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextOverflow
import com.example.weatherapp.network.Day
import com.example.weatherapp.network.ForecastDay
import com.example.weatherapp.network.ForecastModel
//import com.example.weatherapp.network.RetrofitInstance
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.ui.theme.CoolOceanBlue
import com.example.weatherapp.ui.theme.SkyBlue
import com.example.weatherapp.ui.theme.SoftSkyBlye
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


import kotlin.math.roundToInt


@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = hiltViewModel(), modifier: Modifier) {

    var city by remember { mutableStateOf("") }
    var weatherResult = weatherViewModel.weatherResult.observeAsState()
    // val weatherViewModel = WeatherViewModel(WeatherRepository(RetrofitInstance.apiService))
    val forecastResult by weatherViewModel.forecastResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current   // Variable to hide keyboard
    val gradient = Brush.verticalGradient(
        colors = listOf(
            SoftLavender,
            SoftBlue,
            SoftSkyBlye,
            SoftLavender
        )
    )
    //  DeepRoyalPurple,

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        //   contentAlignment = Alignment.Center
        //    .background(color = Purple)

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
                        // fontSize = 22.sp


                        //  color = Color.Black // Ensures label stays black even when focused
                    )
                },
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif, // Uses Arial or the default sans-serif font
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
                ),
                // textStyle = TextStyle(SoftLavender),
                //textStyle = TextStyle(fontSize = 22.sp), // Text of user's input
                shape = RoundedCornerShape(28.dp),


                trailingIcon = {
                    IconButton(
                        onClick = {
                            weatherViewModel.getWeatherData(city)
                            weatherViewModel.getForecastData(city)
                            keyboardController?.hide()          //Question mark because is nullable
                        }                          //Hides keyboard when click search
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Search,   //does not have weight so only takes space of contents
                            contentDescription = "Search for City",
                            modifier = Modifier.size(28.dp),
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    //   cursorColor = MaterialTheme.colorScheme.,
                    cursorColor = Color.Black,
                    focusedContainerColor = MaterialTheme.colorScheme.surface, // Interior background color
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface, // Background when not focused
                    //     focusedContainerColor = Color.White,
                    //    focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                    //     unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
                    //     focusedPlaceholderColor = Color.Black,  // Placeholder color when focused
                    //    unfocusedPlaceholderColor = Color.Black, // Placeholder color when not focused

                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                ),

                //  unfocusedPlaceholderColor = Color.Cyan


                maxLines = 1,
                singleLine = true

            )  // End of OutlinedTextField


            if (weatherResult.value is NetworkResponse.Loading) {
                Spacer(modifier = Modifier.height(48.dp))
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            } else {



                // Main Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(corner = CornerSize(44.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                //    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    //   colors = CardDefaults.cardColors(containerColor = RichPurple.copy(alpha = 0.6f))
                    //     colors = CardDefaults.cardColors(containerColor = DeepPurple)
                       //  colors = CardDefaults.cardColors(containerColor = SoftSkyBlye)
                     //    colors = CardDefaults.cardColors(containerColor = SkyBlue)
                     //   colors = CardDefaults.cardColors(containerColor = SoftLavender)
                      colors = CardDefaults.cardColors(containerColor = SoftBlue)

                ) {
                    // Current Weather Data Handling
                    when (val resultWeather =
                        weatherResult.value) { //.value represents the current state of the object
                        is NetworkResponse.Error -> {
                         //   Spacer(modifier = Modifier.height(48.dp))

                            Text(
                                text = resultWeather.message,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                        }
                        /*
                    NetworkResponse.Loading -> {

                        Spacer(modifier = Modifier.height(48.dp))
                   //     CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                    }
*/
                        is NetworkResponse.Success -> {
                            WeatherDetails(data = resultWeather.data)

                            Spacer(Modifier.height(0.dp))

                            WeatherGridDisplay(data = resultWeather.data)
                        }

                        null -> {}
                        else -> {}
                    }
                }


            } // End of 1st Card (location, temp, image)

            Spacer(Modifier.height(0.dp))


            // Forecast Data Handling
            when (val resultForecast =
                forecastResult) { //.value represents the current state of the object

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
        } // End of Column
    } // End of Box
    // End of WeatherScreen
}
// Details in Main Card (City, icon, temp, feels like
@Composable
fun WeatherDetails(data: WeatherModel) {
    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier.size(400.dp) // Adjust size to control overlap
    )
    {

        // Name of City Display
        Text(
            modifier = Modifier
              //  .fillMaxWidth()
                .align(Alignment.TopCenter) // Adjust position
                .offset(x = 10.dp, y = 0.dp)
               .wrapContentWidth(Alignment.CenterHorizontally), // Ensures proper centering
            // .size(200.dp),
            text = data.location.name.uppercase(),
            //  text = data.location.name.uppercase() + ", ",
            fontStyle = FontStyle.Normal,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.goblin_one_regular)),

                //  style = MaterialTheme.typography.titleLarge,
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
                .align(Alignment.TopCenter) // Adjust position
                .offset(x = (30).dp, y = 40.dp)
                .size(250.dp),
            //  model = "https://cdn.weatherapi.com/weather/64x64/night/248.png",
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
            //    text = " ${data.current.temp_c} ° c",
            fontSize = 150.sp,
            color = Color.Blue.copy(alpha = 0.70f), // 50% transparency
            //  color = Color.Cyan.copy(alpha = 0.80f), // 50% transparency
            //  color =  SoftBlue.copy(alpha = 0.7f),
            //    color =  PastelPeach.copy(alpha = 0.7f),
            // color = SoftSkyBlye.copy(alpha = 0.7f),

            modifier = Modifier
                .align(Alignment.BottomCenter) // Adjust position
                .offset(x = -60.dp, y = 0.dp) // Move slightly over icon
        )

        Text(
            text = "Feels Like: ${data.current.feelslike_c} °C",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Adjust position
                .offset(x = (80).dp, y = 0.dp) // Move slightly over icon
            //   .padding(top = 8.dp) // Adds spacing below the temp
        )
    } // End of Box in WeatherDetails
} // End of WeatherDetails function


// Card for Humidity, HeatIndex, Wind
@Composable
fun WeatherGridDisplay(data: WeatherModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(28.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        //   colors = CardDefaults.cardColors(containerColor = RichPurple.copy(alpha = 0.6f))
        //     colors = CardDefaults.cardColors(containerColor = DeepPurple)
        //  colors = CardDefaults.cardColors(containerColor = SoftSkyBlye)
         //    colors = CardDefaults.cardColors(containerColor = CoolOceanBlue)
        //  colors = CardDefaults.cardColors(containerColor = DeepNightBlue)
          //  colors = CardDefaults.cardColors(containerColor = SoftLavender)
       //   colors = CardDefaults.cardColors(containerColor = SoftBlue)


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
                    .height(40.dp)  // Adjust height to match content
                    .width(1.dp)     // Thin line
            )

            WeatherDetailsItem(
                Icons.Default.Thermostat,
                "Heat Index",
                data.current.heatindex_c + "°C"
            )
            VerticalDivider(
                color = Color.White, // White vertical line
                modifier = Modifier
                    .height(40.dp)  // Adjust height to match content
                    .width(1.dp)     // Thin line
            )

            WeatherDetailsItem(Icons.Default.Air, "Wind", data.current.wind_kph + " km/h")
            //   WeatherDetailsItem("Precip", data.current.precip_in+ "mm")
            //   WeatherDetailsCard("Feels Like", data.current.feelslike_c+ " °C")

        }

    }
}

//
@Composable
fun WeatherDetailsItem(icon: ImageVector, key: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = key,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )

        Text(text = key, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}


// Lazy to show the most recent searches.
@Composable
fun WeatherForecastList(forecastData: ForecastModel) {

    /*
    Card(
        modifier = Modifier
            .fillMaxWidth() // Ensure Card takes full width
            .padding(16.dp),
        shape = RoundedCornerShape(36.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      //  colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        //    colors = CardDefaults.cardColors(containerColor = CoolOceanBlue)
        //  colors = CardDefaults.cardColors(containerColor = DeepNightBlue)
        //  colors = CardDefaults.cardColors(containerColor = SoftLavender)
        //   colors = CardDefaults.cardColors(containerColor = SoftBlue)


    ) {
*/
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
            //verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),

            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(forecastData.forecast.forecastday) { day ->
                WeatherForecast(day) // Pass the ForecastDay object
            }
        }
    }
//} // End of WeatherForecastList Card

// WeatherForecast Details (Date, Avg Temp, Humidity, Wind)
@Composable
fun WeatherForecast(day: ForecastDay) {
    Card(
        modifier = Modifier
            .height(180.dp)
            // .fillMaxWidth(),
            .width(170.dp),

      //  colors = CardDefaults.cardColors(containerColor = SoftLavender),
     //   colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        //    colors = CardDefaults.cardColors(containerColor = CoolOceanBlue),
          colors = CardDefaults.cardColors(containerColor = DeepNightBlue),
        //  colors = CardDefaults.cardColors(containerColor = SoftLavender),
        //   colors = CardDefaults.cardColors(containerColor = SoftBlue),

        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
         //   verticalArrangement = Arrangement.Center
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Display weather condition icon
            AsyncImage(
                model = "https:${day.day.condition.icon}",
                contentDescription = day.day.condition.text,
                modifier = Modifier.size(64.dp)
            )


            Text(
                text = formatDate(day.date), // Displays Date
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                //    verticalAlignment = Alignment.CenterVertically,
                //  horizontalArrangement = Arrangement.SpaceBetween,
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


// Function to format date (optional)
fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    val parsedDate = inputFormat.parse(date) ?: return date
    return outputFormat.format(parsedDate)
}

// End of Code


/*
   Card(
       modifier = Modifier
           .height(120.dp)
           .width(140.dp)

          //.size(150.dp)
           .padding(8.dp),
       shape = RoundedCornerShape(28.dp),
       elevation = CardDefaults.cardElevation(12.dp),
     //  colors = CardDefaults.cardColors(Color.Cyan) // Kind of looks nice
      // colors = CardDefaults.cardColors(containerColor = SoftLavender) // ok
       colors = CardDefaults.cardColors(containerColor = SoftBlue) // ok
      // colors = CardDefaults.cardColors(containerColor = LightTeal)
     //  colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
    //   colors = CardDefaults.cardColors(containerColor = SoftCyan)
     //   colors = CardDefaults.cardColors(containerColor = MutedTeal)
       // colors = CardDefaults.cardColors(containerColor = LightTeal)
    //   colors = CardDefaults.cardColors(containerColor = SoftSkyBlye)
     //     colors = CardDefaults.cardColors(containerColor = SoftSkyBlye)

  */


/*
@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = hiltViewModel(), modifier: Modifier) {

    var city by remember { mutableStateOf("") }
    var weatherResult = weatherViewModel.weatherResult.observeAsState()
  // val weatherViewModel = WeatherViewModel(WeatherRepository(RetrofitInstance.apiService))
    val forecastResult by weatherViewModel.forecastResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current   // Variable to hide keyboard
    val gradient = Brush.verticalGradient(
        colors = listOf(
          //  SoftLavender,
            Purple,
            DeepRoyalPurple,
            //    MaterialTheme.colorScheme.primary,
            //    MaterialTheme.colorScheme.secondary
        )
        // colors = listOf(Color(0xFF2196F3), Color(0xFF6EC6FF) - lighter - hardcoded
        // colors = listOf(Color(0xFF0288D1), Color(0XFF03A9F4) - bit darker
    )
/*
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
        //   .background(color = Purple)

    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .align(Alignment.Center)
        ) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(0f, height * 0.4f) // Start closer to middle
                cubicTo(
                    width * 0.25f, height * 0.8f, // First Control Point
                    width * 0.75f, height * 0.2f, // Second Control Point
                    width, height * 0.5f // End point
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    listOf(
                       // DeepRoyalPurple,
                        CoolOceanBlue,
                          SkyBlue, // looks cool
                      //    DeepNightBlue
                    )
                )
            )
        }
*/
        /*
        // Moon and Stars (Top of Box)
        Icon(
            imageVector = Icons.Default.Brightness2, // Moon
            contentDescription = "Moon",
            tint = Color.Yellow,
            modifier = Modifier
                .padding(16.dp)
                .size(80.dp)
                .align(Alignment.TopEnd)
        )
*/
    /*
        // Star Icon
        Icon(
            imageVector = Icons.Default.Star, // Star
            contentDescription = "String",
            tint = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .size(20.dp)
        )
        */

        /*
        // Mountains (Bottom of Box)
        Icon(
            imageVector = Icons.Default.Terrain, // Mountain
            contentDescription = "Mountains",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.BottomCenter)
        )

 */

        Column(
            //  verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(6.dp)


        ) {

            Spacer(Modifier.height(28.dp))
            /*
            // Title
            Text(
                text = "Wacky Weather",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 36.sp
            )

            */
            Spacer(Modifier.height(28.dp))


                OutlinedTextField(
                    //    modifier = Modifier.weight(1f), // Takes as much space as possible proportional to weight
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(26.dp), // Oustide of Textfield
                     //   .shadow(1.dp, shape = RoundedCornerShape(2.dp)), // Shadow effect for elevation



                            value = city,
                    onValueChange = { city = it },
                    label = {
                        Text(
                            text = "Search for a City",
                            fontSize = 22.sp
                        )
                    },
                    textStyle = TextStyle(fontSize = 22.sp), // Text of user's input
                    shape = RoundedCornerShape(28.dp),

                    trailingIcon = {
                        IconButton(
                            onClick = {
                                weatherViewModel.getWeatherData(city)
                                weatherViewModel.getForecastData(city)
                                keyboardController?.hide()          //Question mark because is nullable
                            }                          //Hides keyboard when click search
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Search,   //does not have weight so only takes space of contents
                                contentDescription = "Search for City",
                                modifier = Modifier.size(28.dp),
                            )
                        }
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                       cursorColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = MaterialTheme.colorScheme.surface, // Interior background color
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface // Background when not focused
                    )

                )


            // Current Weather Data Handling
            when (val resultWeather =
                weatherResult.value) { //.value represents the current state of the object
                is NetworkResponse.Error -> {
                    Spacer(modifier = Modifier.height(48.dp))


                    Text(
                        text = resultWeather.message,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                }

                NetworkResponse.Loading -> {

                    Spacer(modifier = Modifier.height(48.dp))
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                }

                is NetworkResponse.Success -> {
                    WeatherDetails(data = resultWeather.data)
                }

                null -> {}
            }

            Spacer(Modifier.height(16.dp))

            // Forecast Data Handling
            when (val resultForecast =
                forecastResult) { //.value represents the current state of the object

                is NetworkResponse.Error -> {
                    Text(text = "Error loading forecast data", color = Color.Red)
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


    @Composable
    fun WeatherDetails(data: WeatherModel) {

/*
        // Main Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
         //   elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            // colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            colors = CardDefaults.cardColors(containerColor = RichPurple.copy(alpha = 0.6f))
            //      colors = CardDefaults.cardColors(containerColor = DeepPurple)
        ) {
*/
/*
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)  // Reduce space above
            ) {
            */


                Spacer(modifier = Modifier.height(8.dp))
                /*
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {}
                */

                // Name of City Display
                Text(
                    text = data.location.name.uppercase(),
                    //  text = data.location.name.uppercase() + ", ",
                    fontStyle = FontStyle.Normal,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.goblin_one_regular)),

                        //  style = MaterialTheme.typography.titleLarge,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                )
/*
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
*/
                    Box(
                        modifier = Modifier.size(270.dp) // Adjust size to control overlap
                    )
                    {
                    /*
                        // Name of City Display
                        Text(
                            modifier = Modifier
                                .align(Alignment.TopStart) // Adjust position
                                .offset(x = (-50).dp, y = 40.dp), // Move slightly over icon
                            text = data.location.name.uppercase(),
                            //  text = data.location.name.uppercase() + ", ",
                            fontStyle = FontStyle.Normal,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.goblin_one_regular)),

                                //  style = MaterialTheme.typography.titleLarge,
                                fontSize = 32.sp,
                                color = Color.White
                            )
                        )
*/
                        AsyncImage(
                            modifier = Modifier
                                .align(Alignment.TopEnd) // Adjust position
                                .offset(x = (70).dp, y = -40.dp)
                                .size(250.dp),
                            //  model = "https://cdn.weatherapi.com/weather/64x64/night/248.png",
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
                            //    text = " ${data.current.temp_c} ° c",
                            fontSize = 150.sp,
                            //   color = Color.Blue.copy(alpha = 0.70f), // 50% transparency
                            //  color = Color.Cyan.copy(alpha = 0.80f), // 50% transparency
                            //  color =  SoftBlue.copy(alpha = 0.7f),
                            //    color =  PastelPeach.copy(alpha = 0.7f),
                            color = SoftSkyBlye.copy(alpha = 0.7f),

                            modifier = Modifier
                                .align(Alignment.CenterStart) // Adjust position
                                .offset(x = (-70).dp, y = 30.dp) // Move slightly over icon
                        )

                        Text(
                            text = "Feels Like: ${data.current.feelslike_c} °C",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd) // Adjust position
                                .offset(x = (50).dp, y = 70.dp) // Move slightly over icon
                                .padding(top = 8.dp) // Adds spacing below the temp
                        )


                    }
                    //   Spacer(modifier = Modifier.height(12.dp))


            //    } End of Row



         //   } // End of Column


            // BLUE CARD not being used
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(26.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                // colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                colors = CardDefaults.cardColors(containerColor = Color.Blue)


            ) {}

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                /*
                        Text(
                            text = "Current Weather Conditions",
                            fontSize = 24.sp,
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                         */


                //   WeatherForecastList(forecastData = forecastData)
            }


         //      } // End of 1st Card

       //      WeatherForecastList(data)
      //  }   End of 1st card

        WeatherGridDisplay(data)
        Spacer(modifier = Modifier.height(44.dp))

    }


@Composable
fun WeatherGridDisplay(data: WeatherModel) {


 //   Column(
      //  modifier = Modifier.fillMaxSize()
 //   ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()

        )
        {
            WeatherDetailsCard("Humidity", data.current.humidity+ "%")
            WeatherDetailsCard("Heat Index", data.current.heatindex_c+ "°C")
            //   WeatherDetailsCard("Feels Like", data.current.feelslike_c+ " °C")
            WeatherDetailsCard("Wind", data.current.wind_kph+ " km/h")
            //   WeatherDetailsCard("Precip", data.current.precip_in+ "mm")
            /*
            WeatherDetailsCard("Humidity", data.current.humidity+ "%")
            WeatherDetailsCard("Heat Index", data.current.heatindex_c+ "°C")
         //   WeatherDetailsCard("Feels Like", data.current.feelslike_c+ " °C")
            WeatherDetailsCard("Wind", data.current.wind_kph+ " km/h")
         //   WeatherDetailsCard("Precip", data.current.precip_in+ "mm")
*/
        }
        /*
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()

        ) {
            WeatherDetailsCard("Feels Like", data.current.feelslike_c+ " °C")
           WeatherDetailsCard("Wind", data.current.wind_kph+ " km/h")
        }
        */
  //  }
}


@Composable
fun WeatherDetailsCard(key: String, value: String) {

    Text(
        text = key,
        color = Color.White)

    Text(text = value,
        color = Color.White
    )
    /*
    Card(
        modifier = Modifier
            .height(120.dp)
            .width(140.dp)

           //.size(150.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(12.dp),
      //  colors = CardDefaults.cardColors(Color.Cyan) // Kind of looks nice
       // colors = CardDefaults.cardColors(containerColor = SoftLavender) // ok
        colors = CardDefaults.cardColors(containerColor = SoftBlue) // ok
       // colors = CardDefaults.cardColors(containerColor = LightTeal)
      //  colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
     //   colors = CardDefaults.cardColors(containerColor = SoftCyan)
      //   colors = CardDefaults.cardColors(containerColor = MutedTeal)
        // colors = CardDefaults.cardColors(containerColor = LightTeal)
     //   colors = CardDefaults.cardColors(containerColor = SoftSkyBlye)
      //     colors = CardDefaults.cardColors(containerColor = SoftSkyBlye)

    )

    {

     */
    /*
        Column(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = key,
                color = Color.White)

            Text(text = value,
                color = Color.White
                )
*/
        }

//} End of Card

// Add in Lazy Column to display last 5 searches at bottom (scrollable)

// below is the code for a lazy column to show the most recent searches.
// I will need to implement room db/or Firebase RealtimeDB or DataStore to store (to be completed)
@Composable
fun WeatherForecastList(forecastData: ForecastModel) {


    LazyRow(
        contentPadding = PaddingValues(all = 12.dp),
        //verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),

        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(forecastData.forecast.forecastday) { day ->
            WeatherForecast(day) // Pass the ForecastDay object
        }
    }
    }

@Composable
fun WeatherForecast(day: ForecastDay) {
    Card(
        modifier = Modifier
            .height(180.dp)
           // .fillMaxWidth(),
            .width(180.dp)    ,

        colors = CardDefaults.cardColors(containerColor = SoftLavender)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = day.date, // Displays Date
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
            //    verticalAlignment = Alignment.CenterVertically,
              //  horizontalArrangement = Arrangement.SpaceBetween,
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












//Other UI generated below
/*
@Composable
fun WeatherScreen(forecastData: ForecastModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8D7BFF)) // Lighter purple background
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Main Weather Info
            MainWeatherCard(forecastData)

            // Weather Details (Humidity, Wind, Heat Index)
            WeatherDetailsRow(forecastData.forecast.forecastday.first().day)

            // 3-Day Forecast Bar
            ForecastBar(forecastData.forecast.forecastday)
        }
    }
}

@Composable
fun MainWeatherCard(forecastData: ForecastModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5A00C8), RoundedCornerShape(16.dp)) // Rich purple
            .padding(16.dp)
    ) {
        Text(forecastData.location.name, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("${forecastData.forecast.forecastday.first().day.avgTempC}°C", color = Color.Cyan.copy(alpha = 0.8f), fontSize = 48.sp, fontWeight = FontWeight.Bold)
       // Icon(painterResource(id = R.drawable.weather_icon), contentDescription = "Weather Icon", tint = Color.White)
    }
}

@Composable
fun WeatherDetailsRow(day: Day) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD0BFFF), RoundedCornerShape(16.dp)) // Lighter purple
            .padding(16.dp)
    ) {
        WeatherDetailItem(Icons.Default.Lock, "Humidity", "${day.avgHumidity}%")
        WeatherDetailItem(Icons.Default.Home, "Wind", "${day.maxWindKph} kph")
        WeatherDetailItem(Icons.Default.Place, "Heat Index", "${day.avgTempC}°C")
    }
}

@Composable
fun WeatherDetailItem(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = Color.White)
        Text(label, color = Color.White, fontSize = 14.sp)
        Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ForecastBar(forecastDays: List<ForecastDay>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD0BFFF).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(forecastDays) { day ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
                Text(day.date, color = Color.White, fontSize = 14.sp)
                Icon(painterResource(id = R.drawable.baseline_terrain_24), contentDescription = "Weather Icon", tint = Color.White)
                Text("${day.day.avgTempC}°C", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

*/