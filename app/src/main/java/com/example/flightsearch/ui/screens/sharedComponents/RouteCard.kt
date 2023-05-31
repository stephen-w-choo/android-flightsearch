package com.example.flightsearch.ui.screens.sharedComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteWithAirports
import com.example.flightsearch.data.FlightSearchDao
import com.example.flightsearch.ui.FlightSearchViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text(stringResource(id = R.string.depart))
                Row {
                    Text(
                        text = originAirport.iataCode.uppercase(),
                        modifier = modifier.padding(end = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(originAirport.name)
                }
                Text(
                    stringResource(id = R.string.arrive),
                    modifier = modifier.padding(top = 8.dp)
                )
                Row {
                    Text(
                        text = destinationAirport.iataCode.uppercase(),
                        modifier = modifier.padding(end = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(destinationAirport.name)
                }
            }
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
    }
}

//class FakeFlightSearchDao: FlightSearchDao {
//    override fun getAllFavorites(): Flow<List<FavoriteWithAirports>> {
//        return flowOf(listOf()) // Returns an empty list
//    }
//
//    override fun favoriteExists(departureCode: String, destinationCode: String): Flow<Favorite?> {
//        return flowOf(null) // Returns null
//    }
//
//    override fun addFavorite(newFavorite: Favorite) {
//        // Do nothing
//    }
//
//    override fun deleteFavorite(favorite: Favorite) {
//        // Do nothing
//    }
//
//    override fun searchAirports(search: String): Flow<List<Airport>> {
//        return flowOf(listOf()) // Returns an empty list
//    }
//
//    override fun getAllAirports(): Flow<List<Airport>> {
//        return flowOf(listOf()) // Returns an empty list
//    }
//
//    // Override other methods here and provide some dummy implementations
//}
//
//@Preview
//@Composable
//fun RouteCardPreview() {
//    RouteCard(
//        originAirport = Airport(
//            id = 0,
//            name = "London Heathrow",
//            iataCode = "LHR",
//            passengers = 500,
//        ),
//        destinationAirport = Airport(
//            id = 1,
//            name = "New York",
//            iataCode = "JFK",
//            passengers = 500,
//        ),
//        flightSearchViewModel = FlightSearchViewModel(flightSearchDao = FakeFlightSearchDao()),
//        favoriteExists = Favorite(
//            departureCode = "LHR",
//            destinationCode = "JFK",
//        ),
//    )
//}