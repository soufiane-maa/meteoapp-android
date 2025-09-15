package com.babel.meteoapp.utils

import android.util.Log
import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.domain.DailyDetail
import com.babel.meteoapp.domain.ForecastDetails
import com.babel.meteoapp.network.ForecastResponse
import kotlin.math.roundToInt

/**
 * Utility class for validating and debugging weather data
 * Helps identify issues with temperature and other weather data
 */
object DataValidator {
    
    private const val TAG = "DataValidator"
    
    /**
     * Validate and log temperature data for debugging
     */
    fun validateTemperatureData(response: ForecastResponse): CitySummary {
        if (com.babel.meteoapp.BuildConfig.DEBUG) {
            Log.d(TAG, "=== VALIDATING TEMPERATURE DATA ===")
            Log.d(TAG, "City: ${response.city.name}")
            Log.d(TAG, "Number of forecast items: ${response.list.size}")
        }
        
        val first = response.list.firstOrNull()
        if (first == null) {
            if (com.babel.meteoapp.BuildConfig.DEBUG) {
                Log.e(TAG, "ERROR: No forecast data available")
            }
            return createErrorSummary(response.city.name)
        }
        
        val temp = first.main.temp
        val weather = first.weather.firstOrNull()
        
        if (com.babel.meteoapp.BuildConfig.DEBUG) {
            Log.d(TAG, "Raw temperature: $temp")
            Log.d(TAG, "Temperature type: ${temp::class.simpleName}")
            Log.d(TAG, "Weather main: ${weather?.main}")
            Log.d(TAG, "Weather description: ${weather?.description}")
            Log.d(TAG, "Weather icon: ${weather?.icon}")
        }
        
        // Convert Kelvin to Celsius
        val tempCelsius = temp - 273.15
        val roundedTemp = tempCelsius.roundToInt()
        
        if (com.babel.meteoapp.BuildConfig.DEBUG) {
            Log.d(TAG, "Temperature in Kelvin: $temp K")
            Log.d(TAG, "Temperature in Celsius: ${String.format("%.2f", tempCelsius)}°C")
            
            // Validate temperature range (reasonable weather range)
            if (roundedTemp < -50 || roundedTemp > 60) {
                Log.w(TAG, "WARNING: Temperature $roundedTemp°C seems unusual")
            }
            
            Log.d(TAG, "Final temperature: ${roundedTemp}°C")
            Log.d(TAG, "=== END VALIDATION ===")
        }
        
        return CitySummary(
            name = response.city.name,
            temperatureCelsius = roundedTemp,
            weatherMain = weather?.main ?: "Unknown",
            icon = weather?.icon ?: "01d"
        )
    }
    
    /**
     * Validate forecast details data
     */
    fun validateForecastDetails(response: ForecastResponse): ForecastDetails {
        if (com.babel.meteoapp.BuildConfig.DEBUG) {
            Log.d(TAG, "=== VALIDATING FORECAST DETAILS ===")
            Log.d(TAG, "City: ${response.city.name}")
            Log.d(TAG, "Number of forecast items: ${response.list.size}")
        }
        
        val days = response.list.mapIndexed { index, item ->
            if (com.babel.meteoapp.BuildConfig.DEBUG) {
                Log.d(TAG, "Item $index:")
                Log.d(TAG, "  Timestamp: ${item.dt}")
                Log.d(TAG, "  Temperature (K): ${item.main.temp}")
            }
            
            // Convert Kelvin to Celsius
            val tempCelsius = item.main.temp - 273.15
            val roundedTemp = tempCelsius.roundToInt()
            
            if (com.babel.meteoapp.BuildConfig.DEBUG) {
                Log.d(TAG, "  Temperature (C): ${String.format("%.2f", tempCelsius)}°C")
                Log.d(TAG, "  Humidity: ${item.main.humidity}")
                Log.d(TAG, "  Pressure: ${item.main.pressure}")
                Log.d(TAG, "  Wind speed: ${item.wind.speed}")
            }
            
            val weather = item.weather.firstOrNull()
            if (com.babel.meteoapp.BuildConfig.DEBUG) {
                Log.d(TAG, "  Weather: ${weather?.main} - ${weather?.description}")
                Log.d(TAG, "  Icon: ${weather?.icon}")
            }
            
            DailyDetail(
                timestamp = item.dt * 1000,
                temperatureCelsius = roundedTemp,
                humidityPercent = item.main.humidity.roundToInt(),
                windSpeedMetersPerSecond = item.wind.speed,
                pressureHPa = item.main.pressure.roundToInt(),
                weatherMain = weather?.main ?: "Unknown",
                icon = weather?.icon ?: "01d"
            )
        }
        
        if (com.babel.meteoapp.BuildConfig.DEBUG) {
            Log.d(TAG, "=== END FORECAST VALIDATION ===")
        }
        return ForecastDetails(cityName = response.city.name, days = days)
    }
    
    /**
     * Create error summary when data is invalid
     */
    private fun createErrorSummary(cityName: String): CitySummary {
        if (com.babel.meteoapp.BuildConfig.DEBUG) {
            Log.e(TAG, "Creating error summary for $cityName")
        }
        return CitySummary(
            name = cityName,
            temperatureCelsius = 0,
            weatherMain = "Error",
            icon = "01d"
        )
    }
    
    /**
     * Log API response for debugging
     */
    fun logApiResponse(response: ForecastResponse) {
        if (com.babel.meteoapp.BuildConfig.DEBUG) {
            Log.d(TAG, "=== API RESPONSE DEBUG ===")
            Log.d(TAG, "City: ${response.city.name}")
            Log.d(TAG, "Country: ${response.city.country}")
            Log.d(TAG, "Coordinates: ${response.city.coord.lat}, ${response.city.coord.lon}")
            Log.d(TAG, "Forecast items count: ${response.list.size}")
            
            response.list.take(3).forEachIndexed { index, item ->
                Log.d(TAG, "Item $index:")
                Log.d(TAG, "  DT: ${item.dt}")
                Log.d(TAG, "  Main: temp=${item.main.temp}, pressure=${item.main.pressure}, humidity=${item.main.humidity}")
                Log.d(TAG, "  Wind: speed=${item.wind.speed}")
                Log.d(TAG, "  Weather: ${item.weather.map { "${it.main} (${it.description})" }}")
            }
            Log.d(TAG, "=== END API RESPONSE DEBUG ===")
        }
    }
}
