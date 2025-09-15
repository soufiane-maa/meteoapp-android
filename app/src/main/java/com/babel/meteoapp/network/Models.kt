package com.babel.meteoapp.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.babel.meteoapp.R
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    @Json(name = "list") val list: List<ForecastItem>,
    @Json(name = "city") val city: City
)

@JsonClass(generateAdapter = true)
data class City(
    @Json(name = "id") val id: Long?,
    @Json(name = "name") val name: String,
    @Json(name = "coord") val coord: Coord,
    @Json(name = "country") val country: String?
)

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lat") val lat: Double,
    @Json(name = "lon") val lon: Double
)

@JsonClass(generateAdapter = true)
data class ForecastItem(
    @Json(name = "dt") val dt: Long,
    @Json(name = "main") val main: Main,
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "wind") val wind: Wind
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp") val temp: Double,
    @Json(name = "pressure") val pressure: Double,
    @Json(name = "humidity") val humidity: Double
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id") val id: Long,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed") val speed: Double
)


