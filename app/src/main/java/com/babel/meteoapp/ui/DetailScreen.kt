package com.babel.meteoapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.babel.meteoapp.domain.ForecastDetails

@Composable
fun DetailScreen(
    city: String,
    data: ForecastDetails?,
    isLoading: Boolean,
    errorMessage: String?,
    onLoad: (String) -> Unit
) {
    LaunchedEffect(city) { onLoad(city) }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = city, style = MaterialTheme.typography.headlineSmall)
        if (errorMessage != null) Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        if (data != null) {
            val today = data.days.firstOrNull()
            if (today != null) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AsyncImage(model = "https://openweathermap.org/img/wn/${today.icon}@2x.png", contentDescription = null)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "${today.temperatureCelsius}°C - ${today.weatherMain}", style = MaterialTheme.typography.titleLarge)
                        Text(text = "Humidity: ${today.humidityPercent}%")
                        Text(text = "Wind: ${String.format("%.1f", today.windSpeedMetersPerSecond)} m/s")
                        Text(text = "Pressure: ${today.pressureHPa} hPa")
                    }
                }
            }
        }
    }
}


