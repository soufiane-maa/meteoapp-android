package com.babel.meteoapp.data

import android.annotation.SuppressLint
import android.content.Context
import com.babel.meteoapp.domain.location.LocationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationProviderImpl(context: Context) : LocationProvider {
    private val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<Pair<Double, Double>> = runCatching {
        val loc = suspendCancellableCoroutine<android.location.Location> { cont ->
            client.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) cont.resume(location) else cont.resumeWithException(IllegalStateException("No location"))
                }
                .addOnFailureListener { e -> cont.resumeWithException(e) }
        }
        Pair(loc.latitude, loc.longitude)
    }
}


