package com.babel.meteoapp.domain.location

interface LocationProvider {
    suspend fun getCurrentLocation(): Result<Pair<Double, Double>>
}


