package com.babel.meteoapp.data

import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.domain.ForecastDetails
import com.babel.meteoapp.domain.repository.WeatherRepository
import com.babel.meteoapp.domain.toCitySummary
import com.babel.meteoapp.domain.toForecastDetails
import com.babel.meteoapp.network.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val apiKeyProvider: () -> String,
    private val retrofitProvider: RetrofitProvider
) : WeatherRepository {
    private val api = retrofitProvider.apiService

    override suspend fun fetchCitySummary(city: String): Result<CitySummary> = runCatching {
        withContext(Dispatchers.IO) {
            val response = api.getFiveDayForecastByCity(city = city, apiKey = apiKeyProvider())
            response.toCitySummary()
        }
    }

    override suspend fun fetchForecastByCity(city: String): Result<ForecastDetails> = runCatching {
        withContext(Dispatchers.IO) {
            val response = api.getFiveDayForecastByCity(city = city, apiKey = apiKeyProvider())
            response.toForecastDetails()
        }
    }

    override suspend fun fetchForecastByCoords(lat: Double, lon: Double): Result<ForecastDetails> = runCatching {
        withContext(Dispatchers.IO) {
            val response = api.getFiveDayForecastByCoords(lat = lat, lon = lon, apiKey = apiKeyProvider())
            response.toForecastDetails()
        }
    }
}


