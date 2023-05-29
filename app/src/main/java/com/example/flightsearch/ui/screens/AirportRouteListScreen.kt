package com.example.flightsearch.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearch.data.Airport
// import lazy items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import com.example.flightsearch.ui.FlightSearchUiState
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.screens.sharedComponents.RouteCard

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

                // skip if the airport is the current airport
                if (airport.iataCode == uiState.currentAirport.iataCode) {
                    return@items
                }

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

