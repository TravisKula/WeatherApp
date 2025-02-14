package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.weatherui.WeatherScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //used to obtain instance of weatherViewModel
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        //viewModelProvider ensure WeatherViewModel instance persists configuration changes
        setContent {

            WeatherAppTheme { // Wrap the entire app UI with theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                    // pass WeatherViewModel instance to WeatherScreen
                ) { WeatherScreen(weatherViewModel, modifier = Modifier)
                }
                /*
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    */

                }
            }
        }
    }

/*
@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    // Placeholder for your WeatherApp UI
    Text(
        text = "Welcome to WeatherApp!",
        modifier = modifier.padding(16.dp)
    )
}
*/

/*
@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreen(weatherviewModel, modifier = Modifier)
    }
}


 */