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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationDetailScreen(
    data: ForecastDetails?,
    isLoading: Boolean,
    errorMessage: String?,
    onResolveLocationAndLoad: (Double, Double) -> Unit
) {
    val context = LocalContext.current
    val coarsePermission = rememberPermissionState(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    val finePermission = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!coarsePermission.status.isGranted) coarsePermission.launchPermissionRequest()
        if (!finePermission.status.isGranted) finePermission.launchPermissionRequest()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Current location forecast", style = MaterialTheme.typography.headlineSmall)
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
        }) { Text("Use current location") }

        if (data != null) {
            val today = data.days.firstOrNull()
            if (today != null) {
                Text(text = "${data.cityName}: ${today.temperatureCelsius}°C ${today.weatherMain}")
                Text(text = "Humidity: ${today.humidityPercent}%")
                Text(text = "Wind: ${String.format("%.1f", today.windSpeedMetersPerSecond)} m/s")
                Text(text = "Pressure: ${today.pressureHPa} hPa")
            }
        }
    }
}
