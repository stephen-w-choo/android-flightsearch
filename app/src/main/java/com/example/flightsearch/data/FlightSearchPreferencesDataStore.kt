package com.example.flightsearch.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class FlightSearchPreferencesDataStore(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val LAST_SEARCH_QUERY = stringPreferencesKey("last_search_query")

    }

    suspend fun saveLastSearchQuery(lastSearchQuery: String) {
        dataStore.edit { preferences ->
            preferences[LAST_SEARCH_QUERY] = lastSearchQuery
        }
    }

    // database retrieval
    // first part handles possible IO errors and sends out empty preferences
    // second part retrieves the last search query
    // if there is no last search query, it will return an empty string

    val lastSearchQuery: Flow<String> =
        dataStore.data
            .catch {
                if (it is IOException) {
                    Log.e(TAG, "Error reading preferences", it)
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { preferences ->
                preferences[LAST_SEARCH_QUERY] ?: ""
            }
}