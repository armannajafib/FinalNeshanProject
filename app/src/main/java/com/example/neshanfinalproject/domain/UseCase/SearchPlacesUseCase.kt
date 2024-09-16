package com.example.app.domain.usecase

import com.example.neshanfinalproject.domain.model.Location
import com.example.neshanfinalproject.domain.repository.LocationRepository

class SearchPlacesUseCase(private val repository: LocationRepository) {
    suspend fun execute(query: String, latitude: Double, longitude: Double): List<Location> {
        return repository.searchPlaces(query, latitude, longitude)
    }
}
