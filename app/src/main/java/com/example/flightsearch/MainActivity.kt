package com.example.flightsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.flightsearch.ui.FlightSearchRootView
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.theme.FlightSearchTheme
import androidx.lifecycle.viewmodel.compose.viewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlightSearchTheme {
                // A surface container using the 'background' color from the theme
                Surface(){
                    FlightSearchRootView(
                        flightSearchViewModel = viewModel(factory = FlightSearchViewModel.factory)
                    )
                }
            }
        }
    }
}

