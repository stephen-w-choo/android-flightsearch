package com.example.flightsearch.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.flightsearch.ui.FlightSearchUiState
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.screens.sharedComponents.RouteCard

// 2 screens - favourites, airportSearch, airportFlightList
@Composable
fun AllFavoritesScreen(
    uiState: FlightSearchUiState,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    val favorites = flightSearchViewModel.getAllFavorites().collectAsState(initial = emptyList()).value
    LazyColumn(content = {
        items(favorites) { favorite ->
            RouteCard(
                originAirport = favorite.originAirport,
                destinationAirport = favorite.destinationAirport,
                flightSearchViewModel = flightSearchViewModel,
                favoriteExists = favorite.favorite,
            )
        }
    })
}