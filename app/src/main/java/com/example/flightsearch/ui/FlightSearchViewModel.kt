package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FlightSearchDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FlightSearchViewModel(
    private val flightSearchDao: FlightSearchDao
): ViewModel() {
    private val _uiState = MutableStateFlow(FlightSearchUiState())
    val uiState: StateFlow<FlightSearchUiState> = _uiState

    fun setSearchTerm(searchTerm: String) {
        // update uiState with searchTerm string
        if (searchTerm == "") {
            _uiState.value = FlightSearchUiState()
        }
        _uiState.value = _uiState.value.copy(search = searchTerm)
    }

    fun setCurrentAirport(airport: Airport) {
        _uiState.value = _uiState.value.copy(currentAirport = airport)
    }

    fun clearCurrentAirport() {
        _uiState.value = _uiState.value.copy(currentAirport = null)
    }

    fun searchAirports(search: String): Flow<List<Airport>> {
        return flightSearchDao.searchAirports(search)
    }

    fun getAllAirports(): Flow<List<Airport>> {
        return flightSearchDao.getAllAirports()
    }

    fun getAllFavorites(): Flow<List<Favorite>> {
        return flightSearchDao.getAllFavorites()
    }

    fun addFavorite(newFavorite: Favorite) {
        flightSearchDao.addFavorite(newFavorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        flightSearchDao.deleteFavorite(favorite)
    }
}


data class FlightSearchUiState(
    val search: String? = null,
    val currentAirport: Airport? = null,
)