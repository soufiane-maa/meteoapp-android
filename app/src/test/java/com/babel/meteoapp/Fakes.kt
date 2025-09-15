package com.babel.meteoapp

import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.domain.DailyDetail
import com.babel.meteoapp.domain.ForecastDetails
import com.babel.meteoapp.domain.repository.WeatherRepository

class FakeWeatherRepository : WeatherRepository {
    var shouldFail = false

    override suspend fun fetchCitySummary(city: String): Result<CitySummary> {
        return if (shouldFail) Result.failure(IllegalStateException("fail"))
        else Result.success(CitySummary(city, 22, "Sunny", "01d"))
    }

    override suspend fun fetchForecastByCity(city: String): Result<ForecastDetails> {
        return if (shouldFail) Result.failure(IllegalStateException("fail"))
        else Result.success(
            ForecastDetails(
                cityName = city,
                days = listOf(
                    DailyDetail(0L, 22, 50, 3.0, 1015, "Sunny", "01d")
                )
            )
        )
    }

    override suspend fun fetchForecastByCoords(lat: Double, lon: Double): Result<ForecastDetails> {
        return fetchForecastByCity("coords")
    }
}


