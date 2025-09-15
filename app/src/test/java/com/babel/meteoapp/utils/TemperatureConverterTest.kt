package com.babel.meteoapp.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class TemperatureConverterTest {

    @Test
    fun `given kelvin temperature when converting to celsius then returns correct value`() {
        // Given
        val kelvinTemp = 296.84
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(23.69, celsiusTemp, 0.01)
    }

    @Test
    fun `given freezing point in kelvin when converting then returns zero celsius`() {
        // Given
        val kelvinTemp = 273.15
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(0.0, celsiusTemp, 0.01)
    }

    @Test
    fun `given boiling point in kelvin when converting then returns correct celsius`() {
        // Given
        val kelvinTemp = 373.15
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(100.0, celsiusTemp, 0.01)
    }

    @Test
    fun `given very cold temperature in kelvin when converting then returns correct celsius`() {
        // Given
        val kelvinTemp = 250.0
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(-23.15, celsiusTemp, 0.01)
    }

    @Test
    fun `given hot temperature in kelvin when converting then returns correct celsius`() {
        // Given
        val kelvinTemp = 308.15
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(35.0, celsiusTemp, 0.01)
    }

    @Test
    fun `given extreme hot temperature in kelvin when converting then returns correct celsius`() {
        // Given
        val kelvinTemp = 320.0
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(46.85, celsiusTemp, 0.01)
    }

    @Test
    fun `given temperature conversion when rounding then returns correct integer`() {
        // Test cases for rounding
        val testCases = listOf(
            296.84 to 23,
            296.01 to 22,
            307.92 to 34,
            298.33 to 25,
            304.74 to 31
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
    fun `given temperature conversion when using toInt then returns correct value`() {
        // Given
        val kelvinTemp = 296.84
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        val roundedCelsius = celsiusTemp.toInt()
        
        // Then
        assertEquals(23, roundedCelsius) //
    }

    @Test
    fun `given negative kelvin temperature when converting then returns correct celsius`() {
        // Given
        val kelvinTemp = 200.0
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(-73.15, celsiusTemp, 0.01)
    }

    @Test
    fun `given zero kelvin when converting then returns absolute zero celsius`() {
        // Given
        val kelvinTemp = 0.0
        
        // When
        val celsiusTemp = kelvinTemp - 273.15
        
        // Then
        assertEquals(-273.15, celsiusTemp, 0.01)
    }
}
