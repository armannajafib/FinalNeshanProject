//package com.example.app.presentation.main
//
//import androidx.lifecycle.ViewModel
//import com.example.neshanfinalproject.domain.model.Location
//import com.example.app.domain.usecase.GetCurrentLocationUseCase
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class MainViewModel(private val getCurrentLocationUseCase: GetCurrentLocationUseCase) : ViewModel() {
//
//    private val _location = MutableStateFlow<Location?>(null)
//    val location: StateFlow<Location?> = _location
//
//    fun fetchCurrentLocation() {
//        viewModelScope.launch {
//            val location = getCurrentLocationUseCase.execute()
//            _location.value = location
//        }
//    }
//}
