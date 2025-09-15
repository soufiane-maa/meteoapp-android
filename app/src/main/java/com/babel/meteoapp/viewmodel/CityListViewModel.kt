package com.babel.meteoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babel.meteoapp.domain.usecase.GetCitySummaryUseCase
import com.babel.meteoapp.domain.CitySummary
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.babel.meteoapp.R
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val getCitySummary: GetCitySummaryUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val defaultCities = listOf(
        context.getString(R.string.default_city_casablanca),
        context.getString(R.string.default_city_rabat),
        context.getString(R.string.default_city_marrakech),
        context.getString(R.string.default_city_tangier),
        context.getString(R.string.default_city_fes)
    )

    private val _cities = MutableStateFlow(defaultCities)
    private val _searchQuery = MutableStateFlow("")
    private val _isRefreshing = MutableStateFlow(false)
    private val _citySummaries = MutableStateFlow<Map<String, CitySummary>>(emptyMap())
    private val _errorMessage = MutableStateFlow<String?>(null)

    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    val errorMessage: StateFlow<String?> = _errorMessage

    val filteredSummaries: StateFlow<List<CitySummary>> = combine(_cities, _searchQuery, _citySummaries) { cities, query, map ->
        val q = query.trim().lowercase()
        cities
            .filter { city -> q.isEmpty() || city.lowercase().contains(q) }
            .mapNotNull { city -> map[city] }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        refreshAll()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addCity(city: String) {
        val trimmed = city.trim()
        if (trimmed.isEmpty()) return
        if (_cities.value.contains(trimmed)) return
        _cities.value = _cities.value + trimmed
        fetchCitySummary(trimmed)
    }

    fun removeCity(city: String) {
        _cities.value = _cities.value - city
        _citySummaries.value = _citySummaries.value - city
    }

    private var refreshJob: Job? = null
    fun refreshAll() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            _isRefreshing.value = true
            _errorMessage.value = null
            val current = _cities.value
            current.forEach { city -> fetchCitySummary(city) }
            _isRefreshing.value = false
        }
    }

    private fun fetchCitySummary(city: String) {
        viewModelScope.launch {
            getCitySummary(city)
                .onSuccess { summary ->
                    _citySummaries.value = _citySummaries.value + (city to summary.copy(name = city))
                }
                .onFailure { throwable ->
                    _errorMessage.value = throwable.message
                }
        }
    }
}


