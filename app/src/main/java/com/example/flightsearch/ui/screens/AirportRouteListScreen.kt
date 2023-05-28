package com.example.flightsearch.ui.screens

import androidx.compose.foundation.Image
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
import com.example.flightsearch.data.FavoriteWithAirports
import com.example.flightsearch.ui.FlightSearchUiState
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.shared.RouteCard

@Composable
fun AirportRouteListScreen(
    uiState: FlightSearchUiState,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    if (uiState.currentAirport != null) {
        val airportList = flightSearchViewModel
            .getAllAirports()
            .collectAsState(initial = emptyList())
            .value

        LazyColumn(content = {
            items(airportList) { airport ->
                // note - I'm running an N + 1 query here which is not ideal
                // I CAN optimise it by running 2 queries instead - the first to get all the destination airports
                // in the favourites table where the departureCode is the current airport's iataCode
                // and then the second to get all the airports while excluding the ones whose iataCode
                // is in the list of destination airports
                // this will get complicated and unreadable fast, so I'm keeping it simpler for now, albeit inefficient

                // check if the current airport is the same as the destination airport
                if (uiState.currentAirport.iataCode == airport.iataCode) {
                    // if it is, don't show the route card
                    return@items
                }

                var favoriteWithAirports = flightSearchViewModel.checkFavoriteExists(
                    uiState.currentAirport.iataCode,
                    airport.iataCode
                ).collectAsState(initial = null).value
                val favoriteExists = favoriteWithAirports != null
                if (favoriteWithAirports == null) {
                    // make a new FavoriteWithAirport object
                    favoriteWithAirports = FavoriteWithAirports(
                        favorite = Favorite(
                            departureCode = uiState.currentAirport.iataCode,
                            destinationCode = airport.iataCode,
                        ),
                        originAirport = uiState.currentAirport,
                        destinationAirport = airport,
                    )
                }
                RouteCard(
                    favoriteWithAirports = favoriteWithAirports,
                    flightSearchViewModel = flightSearchViewModel,
                    favoriteExists = favoriteExists,
                    modifier = modifier
                )
            }
        })
    }
    Text(text = "Airport Flight List Screen")
}


