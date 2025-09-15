package com.babel.meteoapp.domain.repository

import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.domain.ForecastDetails

interface WeatherRepository {
    suspend fun fetchCitySummary(city: String): Result<CitySummary>
    suspend fun fetchForecastByCity(city: String): Result<ForecastDetails>
    suspend fun fetchForecastByCoords(lat: Double, lon: Double): Result<ForecastDetails>
}


