package com.babel.meteoapp.domain.usecase

import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.domain.ForecastDetails
import com.babel.meteoapp.domain.repository.WeatherRepository

class GetCitySummaryUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): Result<CitySummary> = repository.fetchCitySummary(city)
}

class GetForecastByCityUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): Result<ForecastDetails> = repository.fetchForecastByCity(city)
}

class GetForecastByCoordsUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<ForecastDetails> = repository.fetchForecastByCoords(lat, lon)
}


