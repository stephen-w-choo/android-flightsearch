package com.example.flightsearch.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.flightsearch.ui.screens.AirportRouteListScreen
// import lazy items
import com.example.flightsearch.ui.screens.AirportSearchScreen
import com.example.flightsearch.ui.screens.AllFavoritesScreen
import com.example.flightsearch.ui.theme.FlightSearchTheme

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchRootView(
    flightSearchViewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier,
) {
    // currently using a simple if else series of statements
    // may eventually change to a navController
    val uiState = flightSearchViewModel.uiState.collectAsState().value
    val airportList = flightSearchViewModel.airportList.collectAsState().value
    FlightSearchTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Flight Search") },
                )
            },
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
        ) { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            ) {
                FlightSearchBar(
                    flightSearchViewModel = flightSearchViewModel,
                    uiState = uiState,
                )
                if (uiState.currentAirport != null) {
                    AirportRouteListScreen(
                        uiState = uiState,
                        airportList = airportList,
                        flightSearchViewModel = flightSearchViewModel,
                        modifier = modifier
                    )
                } else if (uiState.search != null) {
                    AirportSearchScreen(
                        uiState = uiState,
                        airportList = airportList,
                        flightSearchViewModel = flightSearchViewModel,
                        modifier = modifier
                    )
                } else {
                    AllFavoritesScreen(
                        uiState = uiState,
                        flightSearchViewModel,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(
    flightSearchViewModel: FlightSearchViewModel,
    uiState: FlightSearchUiState,
    modifier: Modifier = Modifier
) {
    // Text input field
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = uiState.search ?: "",
            onValueChange = {
                flightSearchViewModel.setSearchQuery(it)
            },
            modifier = modifier,
            // round corners
            shape = MaterialTheme.shapes.small,
            // tried to use the onFocusChange modifier here, but I simply could not get it to work
            // focusing worked, but it wouldn't unfocus when I clicked outside the text field
            label = { Text("Enter airport code or name") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        TextButton(
            onClick = {
                flightSearchViewModel.setSearchQuery("")
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Clear")
        }
    }
}


