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
import androidx.compose.ui.platform.LocalContext
import com.babel.meteoapp.R
import androidx.compose.ui.res.dimensionResource
import com.babel.meteoapp.config.ApiConfig

@Composable
fun DetailScreen(
    city: String,
    data: ForecastDetails?,
    isLoading: Boolean,
    errorMessage: String?,
    onLoad: (String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(city) { onLoad(city) }
    Column(modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_lg))) {
        Text(text = city, style = MaterialTheme.typography.headlineSmall)
        if (errorMessage != null) Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        if (data != null) {
            val today = data.days.firstOrNull()
            if (today != null) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = dimensionResource(R.dimen.padding_md)), horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))) {
                        AsyncImage(model = "${ApiConfig.WeatherIcons.BASE_URL}${today.icon}${ApiConfig.WeatherIcons.SUFFIX}", contentDescription = null)
                    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_xs))) {
                        Text(text = context.getString(R.string.temperature_with_weather, today.temperatureCelsius, today.weatherMain), style = MaterialTheme.typography.titleLarge)
                        Text(text = context.getString(R.string.humidity_label, today.humidityPercent))
                        Text(text = context.getString(R.string.wind_label, today.windSpeedMetersPerSecond))
                        Text(text = context.getString(R.string.pressure_label, today.pressureHPa))
                    }
                }
            }
        }
    }
}


