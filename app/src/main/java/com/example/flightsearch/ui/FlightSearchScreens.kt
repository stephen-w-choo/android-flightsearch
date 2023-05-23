package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
// import lazy items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.ui.screens.AirportSearchScreen

/*
Plan your UI

When the user first opens the app, they see an empty screen with a
text field, prompting for an airport.

When the user starts typing, the app displays a list of autocomplete
suggestions that match either the airport name or identifier.

When the user selects a suggestion, the app displays a list of all possible
flights from that airport. Each item includes the identifier and names for both
airports, and a button to save the destination as a favorite. Feel free to experiment with the layout so long as it conveys all necessary information.

When the user clears the search box or does not enter a search query, the app
displays a list of saved favorite destinations, if any exist.

Tip: Use a LazyColumn to display autocomplete suggestions and search results. You
might want to wrap your layout in a Box and use animation APIs to display the
autocomplete suggestions in front of the search results list. Your UI then has two
lazy columns: the search results, which the app always displays, and the autocomplete
suggestions, which the app displays conditionally while the user types.

 */

@Composable
fun FlightSearchMainView(
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    // currently using a simple if else series of statements
    // may eventually change to a navController
    val uiState = flightSearchViewModel.uiState.collectAsState().value

    val searchAirportList = flightSearchViewModel
        .searchAirports(uiState.search ?: "")
        .collectAsState(initial = emptyList())
        .value

    Column {
        FlightSearchBar(flightSearchViewModel = flightSearchViewModel)

        if (uiState.currentAirport != null) {
            AirportRouteListScreen(
                modifier = modifier
            )
        } else if (uiState.search != null) {
            AirportSearchScreen(
                airportList = searchAirportList,
                setCurrentAirport = flightSearchViewModel::setCurrentAirport,
                modifier = modifier
            )
        } else {
            FavouritesScreen(
                favorites = emptyList(),
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
) {
    // Text input field
    val uiState = flightSearchViewModel.uiState.collectAsState().value
    TextField(
        value = uiState.search ?: "",
        onValueChange = {
            flightSearchViewModel.setSearchTerm(it)
        },
        label = { Text("Enter airport code or name") },

    )
}

// 2 screens - favourites, airportSearch, airportFlightList
@Composable
fun FavouritesScreen(
    favorites: List<Favorite>,
    modifier: Modifier = Modifier,
) {
    Text(text = "Favourites Screen")
}



@Composable
fun AirportRouteListScreen(
    modifier: Modifier = Modifier,
) {
    Text(text = "Airport Flight List Screen")
}

@Composable
fun RouteCard(
    currentAirport: Airport,
    destinationAirport: Airport,
    modifier: Modifier = Modifier,
) {
    Text(currentAirport.name)
    Text(destinationAirport.name)
}
