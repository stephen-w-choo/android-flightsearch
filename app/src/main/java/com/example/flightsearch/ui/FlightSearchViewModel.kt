package com.example.flightsearch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApp
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteWithAirports
import com.example.flightsearch.data.FlightSearchDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val flightSearchDao: FlightSearchDao
): ViewModel() {
    private val _uiState = MutableStateFlow(FlightSearchUiState())
    val uiState: StateFlow<FlightSearchUiState> = _uiState

    fun setSearchTerm(searchTerm: String) {
        Log.d("FlightSearchViewModel", "setSearchTerm: $searchTerm")
        if (searchTerm == "") {
            _uiState.value = FlightSearchUiState(search = null)
        } else {
            _uiState.value = _uiState.value.copy(search = searchTerm)
        }
    }

    fun setCurrentAirport(airport: Airport) {
        _uiState.value = _uiState.value.copy(currentAirport = airport)
    }

    fun clearCurrentAirport() {
        _uiState.value = _uiState.value.copy(currentAirport = null)
    }

    fun getAllAirports(): Flow<List<Airport>> {
        return flightSearchDao.getAllAirports()
    }

    fun searchAirports(search: String): Flow<List<Airport>> {
        return flightSearchDao.searchAirports(search)
    }

    fun getAllFavorites(): Flow<List<FavoriteWithAirports>> {
        return flightSearchDao.getAllFavorites()
    }

    fun checkFavoriteExists(departureCode: String, destinationCode: String): Flow<FavoriteWithAirports?> {
        // convert Flow<Int> to Boolean
        return flightSearchDao.favoriteExists(departureCode, destinationCode)
    }

    fun addFavorite(newFavorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("FlightSearchViewModel", "addFavorite: $newFavorite")
            flightSearchDao.addFavorite(newFavorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("FlightSearchViewModel", "deleteFavorite: $favorite")
            flightSearchDao.deleteFavorite(favorite)
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApp)
                FlightSearchViewModel(application.database.flightSearchDao())
            }
        }
    }
}


data class FlightSearchUiState(
    val search: String? = null,
    val currentAirport: Airport? = null,
)