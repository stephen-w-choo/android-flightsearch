package com.example.flightsearch.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


// this is the predefined schema given, although personally I don't think it makes sense
// Favorite should be using the foreign key of Airport, rather than the IATA codes, right?
// clarified with ChatGPT - apparently this keeps it light?
@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String
)

// helper function to compare two favorites ignoring the id
fun Favorite.isSameAs(other: Favorite): Boolean {
    return departureCode == other.departureCode && destinationCode == other.destinationCode
}

@Entity
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    @ColumnInfo
    val passengers: Int,
)

data class FavoriteWithAirports(
    @Embedded val favorite: Favorite,
    @Relation(
        parentColumn = "departure_code",
        entityColumn = "iata_code"
    )
    val originAirport: Airport,
    @Relation(
        parentColumn = "destination_code",
        entityColumn = "iata_code"
    )
    val destinationAirport: Airport
)