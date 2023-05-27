package com.example.flightsearch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearch.data.Airport
// import lazy items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import com.example.flightsearch.R
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.ui.FlightSearchUiState
import com.example.flightsearch.ui.FlightSearchViewModel

@Composable
fun AirportRouteListScreen(
    uiState: FlightSearchUiState,
    airportList: List<Airport>,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    if (uiState.currentAirport != null) {
        LazyColumn(content = {
            items(airportList) { airport ->
                // note - I'm running an N + 1 query here which is not ideal
                // I CAN optimise it by running 2 queries instead - the first to get all the destination airports
                // in the favourites table where the departureCode is the current airport's iataCode
                // and then the second to get all the airports while excluding the ones whose iataCode
                // is in the list of destination airports

                // this will get complicated and unreadable fast, so I'm keeping it simple for now, albeit inefficient
                RouteCard(
                    originAirport = uiState.currentAirport,
                    destinationAirport = airport,
                    flightSearchViewModel = flightSearchViewModel,
                    favoriteExists = flightSearchViewModel.checkFavoriteExists(
                        uiState.currentAirport.iataCode,
                        airport.iataCode
                    ).collectAsState(initial = null).value,
                    modifier = modifier
                )
            }
        })
    }
    Text(text = "Airport Flight List Screen")
}

@Composable
fun RouteCard(
    originAirport: Airport,
    destinationAirport: Airport,
    flightSearchViewModel: FlightSearchViewModel,
    favoriteExists: Favorite?,
    modifier: Modifier = Modifier,
) {
    // if it does, show the remove button
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

@Composable
fun FavoriteButton(
    onClick: () -> Unit,
    favorited: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(onClick = onClick) {
        if (favorited) {
            Image(
                painter = painterResource(id = R.drawable.favorite_star),
                contentDescription = "Currently a favorite. Tap to unfavorite",
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.unfavorite_star),
                contentDescription = "Currently not a favorite. Tap to favorite",
            )
        }
    }
}
