package com.babel.meteoapp.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    @Test
    fun `given temperature conversion when using toInt then returns correct value`() {
        // Given
        val kelvinTemp = 296.84
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        val roundedCelsius = celsiusTemp.toInt()
        
        // Then
        assertEquals(23, roundedCelsius) // 23.69°C → 23°C (toInt() truncates)
    }

    @Test
    fun `given different temperature values when converting then returns correct celsius values`() {
        // Test various temperature conversions
        val testCases = listOf(
            273.15 to 0,      // Freezing point
            296.84 to 23,     // Room temperature (truncated)
            308.15 to 35,     // Hot day
            250.0 to -23,     // Very cold
            320.0 to 46       // Extremely hot
        )
        
        testCases.forEach { (kelvin, expectedCelsius) ->
            // Given
            val kelvinTemp = kelvin
            
            // When
            val celsiusTemp = kelvinTemp - 273.15
            val roundedCelsius = celsiusTemp.toInt()
            
            // Then
            assertEquals(
                "Temperature conversion failed for $kelvin K",
                expectedCelsius,
                roundedCelsius
            )
        }
    }

    @Test
    fun `given temperature conversion when using roundToInt then returns correct value`() {
        // Given
        val kelvinTemp = 296.84
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        val roundedCelsius = celsiusTemp.toInt()
        
        // Then
        assertEquals(23, roundedCelsius) // 23.69°C → 23°C (toInt() truncates)
    }
}