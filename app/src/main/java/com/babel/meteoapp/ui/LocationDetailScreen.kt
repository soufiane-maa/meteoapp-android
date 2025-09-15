package com.babel.meteoapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.babel.meteoapp.domain.ForecastDetails
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import androidx.compose.ui.platform.LocalContext
import com.babel.meteoapp.R
import androidx.compose.ui.res.dimensionResource

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationDetailScreen(
    data: ForecastDetails?,
    isLoading: Boolean,
    errorMessage: String?,
    onResolveLocationAndLoad: (Double, Double) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coarsePermission = rememberPermissionState(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    val finePermission = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!coarsePermission.status.isGranted) coarsePermission.launchPermissionRequest()
        if (!finePermission.status.isGranted) finePermission.launchPermissionRequest()
    }

    Column(modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_lg)), verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))) {
        Text(context.getString(R.string.current_location_forecast), style = MaterialTheme.typography.headlineSmall)
        if (errorMessage != null) Text(errorMessage, color = MaterialTheme.colorScheme.error)
        Button(onClick = {
            val client = LocationServices.getFusedLocationProviderClient(context)
            try {
                client.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        onResolveLocationAndLoad(location.latitude, location.longitude)
                    }
                }
            } catch (e: SecurityException) {
                // ignore; permission not granted
            }
        }) { Text(context.getString(R.string.use_current_location)) }

        if (data != null) {
            val today = data.days.firstOrNull()
            if (today != null) {
                Text(text = context.getString(R.string.city_weather_format, data.cityName, today.temperatureCelsius, today.weatherMain))
                Text(text = context.getString(R.string.humidity_label, today.humidityPercent))
                Text(text = context.getString(R.string.wind_label, today.windSpeedMetersPerSecond))
                Text(text = context.getString(R.string.pressure_label, today.pressureHPa))
            }
        }
    }
}
