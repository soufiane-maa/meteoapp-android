package com.babel.meteoapp.data

import android.util.Log
import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.domain.ForecastDetails
import com.babel.meteoapp.domain.repository.WeatherRepository
import com.babel.meteoapp.domain.toCitySummary
import com.babel.meteoapp.domain.toForecastDetails
import com.babel.meteoapp.network.RetrofitProvider
import com.babel.meteoapp.utils.DataValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val apiKeyProvider: () -> String,
    private val retrofitProvider: RetrofitProvider
) : WeatherRepository {
    private val api = retrofitProvider.apiService

    override suspend fun fetchCitySummary(city: String): Result<CitySummary> = runCatching {
        withContext(Dispatchers.IO) {
            Log.d("WeatherRepository", "Fetching city summary for: $city")
            val response = api.getFiveDayForecastByCity(city = city, apiKey = apiKeyProvider())
            Log.d("WeatherRepository", "API response received for $city")
            
            // Log raw API response for debugging
            DataValidator.logApiResponse(response)
            
            val result = response.toCitySummary()
            Log.d("WeatherRepository", "Converted to CitySummary: $result")
            result
        }
    }

    override suspend fun fetchForecastByCity(city: String): Result<ForecastDetails> = runCatching {
        withContext(Dispatchers.IO) {
            Log.d("WeatherRepository", "Fetching forecast details for: $city")
            val response = api.getFiveDayForecastByCity(city = city, apiKey = apiKeyProvider())
            Log.d("WeatherRepository", "API response received for $city")
            
            // Log raw API response for debugging
            DataValidator.logApiResponse(response)
            
            val result = response.toForecastDetails()
            Log.d("WeatherRepository", "Converted to ForecastDetails: ${result.days.size} days")
            result
        }
    }

    override suspend fun fetchForecastByCoords(lat: Double, lon: Double): Result<ForecastDetails> = runCatching {
        withContext(Dispatchers.IO) {
            val response = api.getFiveDayForecastByCoords(lat = lat, lon = lon, apiKey = apiKeyProvider())
            response.toForecastDetails()
        }
    }
}


