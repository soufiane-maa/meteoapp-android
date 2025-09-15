package com.babel.meteoapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.babel.meteoapp.domain.CitySummary
import com.babel.meteoapp.domain.ForecastDetails

/**
 * Debug panel to display raw weather data for troubleshooting
 * Only shown in debug builds
 */
@Composable
fun DebugPanel(
    citySummary: CitySummary?,
    forecastDetails: ForecastDetails?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "🐛 DEBUG DATA",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            
            // City Summary Debug
            citySummary?.let { summary ->
                Text(
                    text = "City Summary:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Name: ${summary.name}",
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Temperature: ${summary.temperatureCelsius}°C",
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Weather: ${summary.weatherMain}",
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Icon: ${summary.icon}",
                    fontFamily = FontFamily.Monospace
                )
            }
            
            // Forecast Details Debug
            forecastDetails?.let { forecast ->
                Text(
                    text = "Forecast Details:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "City: ${forecast.cityName}",
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Days count: ${forecast.days.size}",
                    fontFamily = FontFamily.Monospace
                )
                
                forecast.days.take(3).forEachIndexed { index, day ->
                    Text(
                        text = "Day $index:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "  Temp: ${day.temperatureCelsius}°C",
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "  Humidity: ${day.humidityPercent}%",
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "  Pressure: ${day.pressureHPa} hPa",
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "  Wind: ${day.windSpeedMetersPerSecond} m/s",
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "  Weather: ${day.weatherMain}",
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "  Icon: ${day.icon}",
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
            
            if (citySummary == null && forecastDetails == null) {
                Text(
                    text = "No data available",
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
