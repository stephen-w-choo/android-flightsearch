package com.example.flightsearch.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearch.data.Airport


@Composable
fun AirportSearchScreen(
    airportList: List<Airport>,
    setCurrentAirport: (Airport) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn() {
        items(airportList) { airport ->
            AirportCard(
                airport = airport,
                setCurrentAirport = setCurrentAirport
            )
        }
    }
}

@Composable
fun AirportCard(
    airport: Airport,
    setCurrentAirport: (Airport) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { setCurrentAirport(airport) }
    ) {
        Text(
            text = airport.iataCode.uppercase(),
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(4.dp)
                .padding(start = 4.dp, end = 4.dp)
        )
        Text(
            text = airport.name,
            modifier = modifier.
            padding(4.dp)
        )
    }
}