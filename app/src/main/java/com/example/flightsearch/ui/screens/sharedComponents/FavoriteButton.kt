package com.example.flightsearch.ui.screens.sharedComponents

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.flightsearch.R

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