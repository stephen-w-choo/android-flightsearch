package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.FlightSearchDatabase
import com.example.flightsearch.data.FlightSearchPreferencesDataStore

private const val FLIGHT_SEARCH_PREFERENCE_NAME = "flight_search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FLIGHT_SEARCH_PREFERENCE_NAME
)

class FlightSearchApp: Application() {
    // initialise flight search SQLite db
    // can be done either via lateinit or lazy
    // let's do it via lateinit to minimise load times
    lateinit var database: FlightSearchDatabase
    lateinit var preferences: FlightSearchPreferencesDataStore

    override fun onCreate() {
        super.onCreate()
        database = FlightSearchDatabase.getDatabase(this)
        preferences = FlightSearchPreferencesDataStore(dataStore)
    }
}