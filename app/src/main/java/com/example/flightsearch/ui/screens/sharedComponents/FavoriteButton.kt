package com.example.flightsearch.ui.screens.sharedComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R

@Composable
fun FavoriteButton(
    onClick: () -> Unit,
    favorited: Boolean,
    modifier: Modifier = Modifier,
) {
    TextButton(onClick = onClick) {
        if (favorited) {
            Image(
                painter = painterResource(id = R.drawable.favorite_star),
                contentDescription = "Currently a favorite. Tap to unfavorite",
                modifier = modifier.size(36.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.unfavorite_star),
                contentDescription = "Currently not a favorite. Tap to favorite",
            )
        }
    }
}