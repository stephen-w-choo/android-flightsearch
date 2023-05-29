package com.example.flightsearch.ui.screens.sharedComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.ui.FlightSearchViewModel

@Composable
fun RouteCard(
    originAirport: Airport,
    destinationAirport: Airport,
    flightSearchViewModel: FlightSearchViewModel,
    favoriteExists: Favorite?,
    modifier: Modifier = Modifier,
) {
    // if favoriteExists, show the remove button
    // if it doesn't, show the add button

    Text(originAirport.name)
    Text(destinationAirport.name)
    FavoriteButton(
        onClick = {
            if (favoriteExists != null) {
                flightSearchViewModel.deleteFavorite(favoriteExists)
            } else {
                // create a favorite object
                val newFavorite = Favorite(
                    departureCode = originAirport.iataCode,
                    destinationCode = destinationAirport.iataCode
                )
                flightSearchViewModel.addFavorite(newFavorite)
            }
        },
        favorited = favoriteExists != null,
        modifier = modifier
    )
}