package com.example.flightsearch.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearch.data.FavoriteWithAirports
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.shared.RouteCard

// 2 screens - favourites, airportSearch, airportFlightList
@Composable
fun FavoritesScreen(
    favorites: List<FavoriteWithAirports>,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(content = {
        items(favorites) { favoriteWithAirports ->
            RouteCard(
                favoriteWithAirports = favoriteWithAirports,
                flightSearchViewModel = flightSearchViewModel,
                favoriteExists = true,
                modifier = modifier,
            )
        }
    })
}