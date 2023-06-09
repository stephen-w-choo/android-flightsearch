package com.example.flightsearch.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearch.data.Airport
// import lazy items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.ui.FlightSearchUiState
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.screens.sharedComponents.RouteCard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AirportRouteListScreen(
    uiState: FlightSearchUiState,
    airportList: List<Airport>,
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(4.dp)
    ) {

        Text(
            text = "Flights from ${uiState.currentAirport?.iataCode?.uppercase() ?: ""}",
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(4.dp)
                .padding(top = 4.dp, bottom = 4.dp)
        )
        if (uiState.currentAirport != null) {
            LazyColumn(content = {
                items(airportList) { airport ->
                    if (airport.iataCode != uiState.currentAirport.iataCode) {
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
                }
            })
        }
    }
}

