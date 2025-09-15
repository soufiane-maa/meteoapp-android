package com.babel.meteoapp.config

/**
 * Centralized API configuration for the application
 * This class contains all API-related constants and configurations
 */
object ApiConfig {
    
    /**
     * Base URL for the OpenWeatherMap API
     * This should be the only place where the base URL is defined
     */
    const val BASE_URL = "https://api.openweathermap.org/"
    
    /**
     * API endpoints
     */
    object Endpoints {
        const val FORECAST = "data/2.5/forecast"
        const val CURRENT_WEATHER = "data/2.5/weather"
        const val GEOCODING = "geo/1.0/direct"
    }
    
    /**
     * API parameters
     */
    object Parameters {
        const val UNITS = "metric"
        const val LANG = "en"
        const val APP_ID = "appid"
    }
    
    /**
     * Weather icon configuration
     */
    object WeatherIcons {
        const val BASE_URL = "https://openweathermap.org/img/wn/"
        const val SUFFIX = "@2x.png"
    }
    
    /**
     * Request timeouts (in milliseconds)
     */
    object Timeouts {
        const val CONNECT_TIMEOUT = 30_000L
        const val READ_TIMEOUT = 30_000L
        const val WRITE_TIMEOUT = 30_000L
    }
    
    /**
     * Cache configuration
     */
    object Cache {
        const val MAX_AGE = 300L // 5 minutes
        const val MAX_STALE = 86400L // 24 hours
    }
}
