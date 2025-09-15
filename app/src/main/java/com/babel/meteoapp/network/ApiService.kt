package com.babel.meteoapp.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.babel.meteoapp.config.ApiConfig
import com.babel.meteoapp.R
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ApiService {
    // OpenWeatherMap 5 day / 3 hour forecast
    // https://api.openweathermap.org/data/2.5/forecast?q={city name}&appid={API key}&units=metric
    @GET(ApiConfig.Endpoints.FORECAST)
    suspend fun getFiveDayForecastByCity(
        @Query("q") city: String,
        @Query(ApiConfig.Parameters.APP_ID) apiKey: String,
        @Query(ApiConfig.Parameters.UNITS) units: String = ApiConfig.Parameters.UNITS
    ): ForecastResponse

    // https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}&units=metric
    @GET(ApiConfig.Endpoints.FORECAST)
    suspend fun getFiveDayForecastByCoords(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query(ApiConfig.Parameters.APP_ID) apiKey: String,
        @Query(ApiConfig.Parameters.UNITS) units: String = ApiConfig.Parameters.UNITS
    ): ForecastResponse
}


