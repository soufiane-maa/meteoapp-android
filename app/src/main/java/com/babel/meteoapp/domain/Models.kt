package com.babel.meteoapp.domain

data class CitySummary(
    val name: String,
    val temperatureCelsius: Int,
    val weatherMain: String,
    val icon: String
)

data class DailyDetail(
    val timestamp: Long,
    val temperatureCelsius: Int,
    val humidityPercent: Int,
    val windSpeedMetersPerSecond: Double,
    val pressureHPa: Int,
    val weatherMain: String,
    val icon: String
)

data class ForecastDetails(
    val cityName: String,
    val days: List<DailyDetail>
)


