package com.example.app.data.repository

import com.example.app.data.remote.NeshanApiService
import com.example.neshanfinalproject.domain.model.Location
import com.example.neshanfinalproject.domain.repository.LocationRepository

class LocationRepositoryImpl(private val apiService: NeshanApiService) : LocationRepository {

    override suspend fun getCurrentLocation(): Location {
        // موقعیت کاربر می‌تواند از GPS یا روش دیگر به دست بیاید
        return Location(35.6892, 51.3890, "Tehran")
    }

    override suspend fun searchPlaces(query: String, latitude: Double, longitude: Double): List<Location> {
        return apiService.searchPlaces(query, latitude, longitude)
    }
}
