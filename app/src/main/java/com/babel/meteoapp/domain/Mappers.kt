package com.babel.meteoapp.domain

import com.babel.meteoapp.network.ForecastResponse
import kotlin.math.roundToInt

fun ForecastResponse.toCitySummary(): CitySummary {
    val first = list.firstOrNull()
    val temp = first?.main?.temp ?: 0.0
    val weather = first?.weather?.firstOrNull()
    return CitySummary(
        name = city.name,
        temperatureCelsius = temp.roundToInt(),
        weatherMain = weather?.main ?: "",
        icon = weather?.icon ?: ""
    )
}

fun ForecastResponse.toForecastDetails(): ForecastDetails {
    val days = list.map { item ->
        val weather = item.weather.firstOrNull()
        DailyDetail(
            timestamp = item.dt * 1000,
            temperatureCelsius = item.main.temp.roundToInt(),
            humidityPercent = item.main.humidity.roundToInt(),
            windSpeedMetersPerSecond = item.wind.speed,
            pressureHPa = item.main.pressure.roundToInt(),
            weatherMain = weather?.main ?: "",
            icon = weather?.icon ?: ""
        )
    }
    return ForecastDetails(cityName = city.name, days = days)
}


