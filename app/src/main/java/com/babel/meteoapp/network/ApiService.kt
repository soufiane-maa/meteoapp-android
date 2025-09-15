package com.babel.meteoapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // OpenWeatherMap 5 day / 3 hour forecast
    // https://api.openweathermap.org/data/2.5/forecast?q={city name}&appid={API key}&units=metric
    @GET("data/2.5/forecast")
    suspend fun getFiveDayForecastByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse

    // https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}&units=metric
    @GET("data/2.5/forecast")
    suspend fun getFiveDayForecastByCoords(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}


