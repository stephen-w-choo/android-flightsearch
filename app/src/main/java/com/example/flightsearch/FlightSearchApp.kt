package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.FlightSearchDatabase

class FlightSearchApp: Application() {
    // initialise flight search SQLite db
    // can be done either via lateinit or lazy
    // let's do it via lateinit to minimise load times
    lateinit var database: FlightSearchDatabase

    override fun onCreate() {
        super.onCreate()
        database = FlightSearchDatabase.getDatabase(this)
    }

}