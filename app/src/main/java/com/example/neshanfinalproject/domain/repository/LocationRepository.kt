package com.example.neshanfinalproject.domain.repository

import com.example.neshanfinalproject.domain.model.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
    suspend fun searchPlaces(query: String, latitude: Double, longitude: Double): List<Location>
}
