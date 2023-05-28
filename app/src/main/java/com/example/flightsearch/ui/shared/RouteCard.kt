package com.example.flightsearch.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteWithAirports
import com.example.flightsearch.ui.FlightSearchViewModel

@Composable
fun RouteCard(
    favoriteWithAirports: FavoriteWithAirports,
    flightSearchViewModel: FlightSearchViewModel,
    favoriteExists: Boolean,
    modifier: Modifier = Modifier,
) {
    // if favoriteExists, show the remove button
    // if it doesn't, show the add button

    Text(favoriteWithAirports.originAirport.name)
    Text(favoriteWithAirports.destinationAirport.name)
    FavoriteButton(
        onClick = {
            if (favoriteExists) {
                flightSearchViewModel.deleteFavorite(favoriteWithAirports.favorite)
            } else {
                // create a favorite object
                flightSearchViewModel.addFavorite(favoriteWithAirports.favorite)
            }
        },
        favorited = favoriteExists,
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