package com.example.app.domain.usecase

import com.example.neshanfinalproject.domain.model.Location
import com.example.neshanfinalproject.domain.repository.LocationRepository

class GetCurrentLocationUseCase(private val repository: LocationRepository) {
    suspend fun execute(): Location {
        return repository.getCurrentLocation()
    }
}
