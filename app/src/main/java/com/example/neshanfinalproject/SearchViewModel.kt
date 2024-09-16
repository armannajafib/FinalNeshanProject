package com.example.app.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neshanfinalproject.domain.model.Location
import com.example.app.domain.usecase.SearchPlacesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val searchPlacesUseCase: SearchPlacesUseCase) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Location>>(emptyList())
    val searchResults: StateFlow<List<Location>> = _searchResults

    fun searchPlaces(query: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val results = searchPlacesUseCase.execute(query, latitude, longitude)
            _searchResults.value = results
        }
    }
}
