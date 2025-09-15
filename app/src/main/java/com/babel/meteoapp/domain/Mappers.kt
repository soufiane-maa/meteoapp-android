package com.babel.meteoapp.domain

import com.babel.meteoapp.network.ForecastResponse
import com.babel.meteoapp.utils.DataValidator
import kotlin.math.roundToInt

fun ForecastResponse.toCitySummary(): CitySummary {
    // Use validator for debugging and validation
    return DataValidator.validateTemperatureData(this)
}

fun ForecastResponse.toForecastDetails(): ForecastDetails {
    // Use validator for debugging and validation
    return DataValidator.validateForecastDetails(this)
}

/**
 * Original mapper functions without validation (for comparison)
 */
fun ForecastResponse.toCitySummaryOriginal(): CitySummary {
    val first = list.firstOrNull()
    val tempKelvin = first?.main?.temp ?: 0.0
    // Convert Kelvin to Celsius
    val tempCelsius = tempKelvin - 273.15
    val weather = first?.weather?.firstOrNull()
    return CitySummary(
        name = city.name,
        temperatureCelsius = tempCelsius.roundToInt(),
        weatherMain = weather?.main ?: "",
        icon = weather?.icon ?: ""
    )
}

fun ForecastResponse.toForecastDetailsOriginal(): ForecastDetails {
    val days = list.map { item ->
        val weather = item.weather.firstOrNull()
        // Convert Kelvin to Celsius
        val tempCelsius = item.main.temp - 273.15
        DailyDetail(
            timestamp = item.dt * 1000,
            temperatureCelsius = tempCelsius.roundToInt(),
            humidityPercent = item.main.humidity.roundToInt(),
            windSpeedMetersPerSecond = item.wind.speed,
            pressureHPa = item.main.pressure.roundToInt(),
            weatherMain = weather?.main ?: "",
            icon = weather?.icon ?: ""
        )
    }
    return ForecastDetails(cityName = city.name, days = days)
}


