package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<FavoriteWithAirports>>

    @Query("SELECT * FROM favorite " +
            "WHERE departure_code = :departureCode " +
            "AND destination_code = :destinationCode")
    fun favoriteExists(departureCode: String, destinationCode: String): Flow<Favorite?>

    @Insert
    fun addFavorite(newFavorite: Favorite)

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM airport " +
            "WHERE name LIKE '%' || :search || '%' " +
            "OR iata_code = :search " +
            "ORDER BY passengers DESC")
    fun searchAirports(search: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport " +
            "ORDER BY passengers DESC")
    fun getAllAirports(): Flow<List<Airport>>
}