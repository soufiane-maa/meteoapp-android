package com.babel.meteoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babel.meteoapp.domain.usecase.GetForecastByCityUseCase
import com.babel.meteoapp.domain.usecase.GetForecastByCoordsUseCase
import com.babel.meteoapp.domain.ForecastDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getForecastByCity: GetForecastByCityUseCase,
    private val getForecastByCoords: GetForecastByCoordsUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _details = MutableStateFlow<ForecastDetails?>(null)

    val isLoading: StateFlow<Boolean> = _isLoading
    val errorMessage: StateFlow<String?> = _errorMessage
    val details: StateFlow<ForecastDetails?> = _details

    fun loadByCity(city: String) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            getForecastByCity(city)
                .onSuccess { data -> _details.value = data }
                .onFailure { t -> _errorMessage.value = t.message }
            _isLoading.value = false
        }
    }

    fun loadByCoords(lat: Double, lon: Double) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            getForecastByCoords(lat, lon)
                .onSuccess { data -> _details.value = data }
                .onFailure { t -> _errorMessage.value = t.message }
            _isLoading.value = false
        }
    }
}


