package com.example.flightsearch.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.ui.FlightSearchUiState
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.screens.sharedComponents.RouteCard

// 2 screens - favourites, airportSearch, airportFlightList
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AllFavoritesScreen(
    uiState: FlightSearchUiState,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    val favorites = flightSearchViewModel.getAllFavorites().collectAsState(initial = emptyList()).value
    Text(
        text = "Favourites",
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(8.dp)
            .padding(top = 4.dp, bottom = 4.dp)
    )

    LazyColumn(content = {
        items(favorites) { favorite ->
            RouteCard(
                originAirport = favorite.originAirport,
                destinationAirport = favorite.destinationAirport,
                flightSearchViewModel = flightSearchViewModel,
                favoriteExists = favorite.favorite,
                modifier = modifier
            )
        }
    })
}