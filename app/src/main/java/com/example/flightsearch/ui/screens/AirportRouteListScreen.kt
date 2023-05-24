package com.example.flightsearch.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flightsearch.data.Airport
// import lazy items
import androidx.compose.foundation.lazy.items
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.isSameAs

@Composable
fun AirportRouteListScreen(
    currentAirport: Airport,
    addFavorite: (Favorite) -> Unit,
    deleteFavorite: (Favorite) -> Unit,
    favoriteList: List<Favorite>,
    allAirportsList: List<Airport>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(content = {
        items(allAirportsList) { airport ->
            RouteCard(
                currentAirport = currentAirport,
                destinationAirport = airport,
                addFavorite = addFavorite,
                deleteFavorite = deleteFavorite,
                favoriteList = favoriteList,
                modifier = modifier
            )
        }
    })
    Text(text = "Airport Flight List Screen")
}

@Composable
fun RouteCard(
    currentAirport: Airport,
    destinationAirport: Airport,
    addFavorite: (Favorite) -> Unit,
    deleteFavorite: (Favorite) -> Unit,
    favoriteList: List<Favorite>,
    modifier: Modifier = Modifier,
) {
    // create a favorite object
    val newFavorite = Favorite(
        departureCode = currentAirport.iataCode,
        destinationCode = destinationAirport.iataCode
    )
    // check if it exists in the favorites list
    val favoriteExists: Boolean = favoriteList.any{ it.isSameAs(newFavorite) }

    // if it does, show the remove button
    // if it doesn't, show the add button

    Text(currentAirport.name)
    Text(destinationAirport.name)
    if (favoriteExists) {
        DeleteFavoriteButton(
            favorite = newFavorite,
            deleteFavorite = deleteFavorite,
            modifier = modifier
        )
    } else {
        AddFavoriteButton(
            favorite = newFavorite,
            addFavorite = addFavorite,
            modifier = modifier
        )
    }
}

@Composable
fun AddFavoriteButton(
    favorite: Favorite,
    addFavorite: (Favorite) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Add Favorite",
        modifier = modifier
            .clickable { addFavorite(favorite) }
    )
}

@Composable
fun DeleteFavoriteButton(
    favorite: Favorite,
    deleteFavorite: (Favorite) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Delete Favorite",
        modifier = modifier
            .clickable { deleteFavorite(favorite) }
    )
}