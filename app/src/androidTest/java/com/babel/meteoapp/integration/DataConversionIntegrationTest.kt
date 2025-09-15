package com.babel.meteoapp.integration

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.babel.meteoapp.domain.toCitySummary
import com.babel.meteoapp.domain.toForecastDetails
import com.babel.meteoapp.network.City
import com.babel.meteoapp.network.Coord
import com.babel.meteoapp.network.ForecastItem
import com.babel.meteoapp.network.ForecastResponse
import com.babel.meteoapp.network.Main
import com.babel.meteoapp.network.Weather
import com.babel.meteoapp.network.Wind
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test d'intégration pour la conversion de données
 * Teste l'intégration entre les modèles réseau et les modèles de domaine
 */
@RunWith(AndroidJUnit4::class)
class DataConversionIntegrationTest {

    @Test
    fun givenValidForecastResponseWhenConvertingToCitySummaryThenReturnsCorrectData() {
        // Given
        val forecastResponse = createValidForecastResponse()

        // When
        val result = forecastResponse.toCitySummary()

        // Then
        assertEquals("City name should match", "Casablanca", result.name)
        assertNotNull("Temperature should not be null", result.temperatureCelsius)
        assertNotNull("Weather main should not be null", result.weatherMain)
        assertNotNull("Icon should not be null", result.icon)
        
        // Vérifier que la température est dans une plage raisonnable
        assertTrue("Temperature should be reasonable", 
            result.temperatureCelsius in -50..60)
    }

    @Test
    fun givenValidForecastResponseWhenConvertingToForecastDetailsThenReturnsCorrectData() {
        // Given
        val forecastResponse = createValidForecastResponse()

        // When
        val result = forecastResponse.toForecastDetails()

        // Then
        assertEquals("City name should match", "Casablanca", result.cityName)
        assertTrue("Should have forecast days", result.days.isNotEmpty())
        
        // Vérifier le premier jour
        val firstDay = result.days.first()
        assertNotNull("Temperature should not be null", firstDay.temperatureCelsius)
        assertNotNull("Weather main should not be null", firstDay.weatherMain)
        assertNotNull("Icon should not be null", firstDay.icon)
        assertTrue("Humidity should be reasonable", 
            firstDay.humidityPercent in 0..100)
        assertTrue("Pressure should be reasonable", 
            firstDay.pressureHPa in 800..1200)
        assertTrue("Wind speed should be reasonable", 
            firstDay.windSpeedMetersPerSecond >= 0)
    }

    @Test
    fun givenMultipleForecastItemsWhenConvertingThenReturnsAllItems() {
        // Given
        val forecastResponse = createForecastResponseWithMultipleItems()

        // When
        val result = forecastResponse.toForecastDetails()

        // Then
        assertEquals("Should have 3 forecast days", 3, result.days.size)
        
        // Vérifier que chaque jour a des données valides
        result.days.forEach { day ->
            assertNotNull("Temperature should not be null", day.temperatureCelsius)
            assertNotNull("Weather main should not be null", day.weatherMain)
            assertNotNull("Icon should not be null", day.icon)
            assertTrue("Temperature should be reasonable", 
                day.temperatureCelsius in -50..60)
        }
    }

    @Test
    fun givenEmptyWeatherListWhenConvertingThenHandlesGracefully() {
        // Given
        val forecastResponse = createForecastResponseWithEmptyWeather()

        // When
        val result = forecastResponse.toCitySummary()

        // Then
        assertEquals("City name should match", "Casablanca", result.name)
        assertNotNull("Temperature should not be null", result.temperatureCelsius)
        assertEquals("Weather main should be Unknown", "Unknown", result.weatherMain)
        assertEquals("Icon should be default", "01d", result.icon)
    }

    private fun createValidForecastResponse(): ForecastResponse {
        return ForecastResponse(
            list = listOf(
                ForecastItem(
                    dt = 1757970000L,
                    main = Main(
                        temp = 296.84, // 23.69°C
                        pressure = 1018.0,
                        humidity = 75.0
                    ),
                    weather = listOf(
                        Weather(
                            id = 801L,
                            main = "Clouds",
                            description = "few clouds",
                            icon = "02n"
                        )
                    ),
                    wind = Wind(speed = 1.46)
                )
            ),
            city = City(
                id = 2553604L,
                name = "Casablanca",
                coord = Coord(lat = 33.5928, lon = -7.6192),
                country = "MA"
            )
        )
    }

    private fun createForecastResponseWithMultipleItems(): ForecastResponse {
        return ForecastResponse(
            list = listOf(
                ForecastItem(
                    dt = 1757970000L,
                    main = Main(temp = 296.84, pressure = 1018.0, humidity = 75.0),
                    weather = listOf(Weather(801L, "Clouds", "few clouds", "02n")),
                    wind = Wind(speed = 1.46)
                ),
                ForecastItem(
                    dt = 1758056400L,
                    main = Main(temp = 298.15, pressure = 1020.0, humidity = 70.0),
                    weather = listOf(Weather(800L, "Clear", "clear sky", "01d")),
                    wind = Wind(speed = 2.1)
                ),
                ForecastItem(
                    dt = 1758142800L,
                    main = Main(temp = 295.37, pressure = 1015.0, humidity = 80.0),
                    weather = listOf(Weather(500L, "Rain", "light rain", "10d")),
                    wind = Wind(speed = 3.2)
                )
            ),
            city = City(
                id = 2553604L,
                name = "Casablanca",
                coord = Coord(lat = 33.5928, lon = -7.6192),
                country = "MA"
            )
        )
    }

    private fun createForecastResponseWithEmptyWeather(): ForecastResponse {
        return ForecastResponse(
            list = listOf(
                ForecastItem(
                    dt = 1757970000L,
                    main = Main(
                        temp = 296.84,
                        pressure = 1018.0,
                        humidity = 75.0
                    ),
                    weather = emptyList(), // Empty weather list
                    wind = Wind(speed = 1.46)
                )
            ),
            city = City(
                id = 2553604L,
                name = "Casablanca",
                coord = Coord(lat = 33.5928, lon = -7.6192),
                country = "MA"
            )
        )
    }
}
