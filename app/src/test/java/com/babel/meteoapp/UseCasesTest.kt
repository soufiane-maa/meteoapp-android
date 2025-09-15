package com.babel.meteoapp

import com.babel.meteoapp.domain.usecase.GetCitySummaryUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UseCasesTest {
    
    @Test
    fun `given valid city name when fetching city summary then returns success`() = runTest {
        // Given
        val repo = FakeWeatherRepository()
        val useCase = GetCitySummaryUseCase(repo)
        
        // When
        val result = useCase("Rabat")
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals("Rabat", result.getOrThrow().name)
        assertEquals(22, result.getOrThrow().temperatureCelsius)
        assertEquals("Sunny", result.getOrThrow().weatherMain)
        assertEquals("01d", result.getOrThrow().icon)
    }

    @Test
    fun `given repository failure when fetching city summary then returns failure`() = runTest {
        // Given
        val repo = FakeWeatherRepository().apply { shouldFail = true }
        val useCase = GetCitySummaryUseCase(repo)
        
        // When
        val result = useCase("Rabat")
        
        // Then
        assertFalse(result.isSuccess)
        assertTrue(result.isFailure)
    }

    @Test
    fun `given empty city name when fetching city summary then returns success with empty name`() = runTest {
        // Given
        val repo = FakeWeatherRepository()
        val useCase = GetCitySummaryUseCase(repo)
        
        // When
        val result = useCase("")
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals("", result.getOrThrow().name)
    }

    @Test
    fun `given different city names when fetching then returns correct city data`() = runTest {
        // Given
        val repo = FakeWeatherRepository()
        val useCase = GetCitySummaryUseCase(repo)
        val cities = listOf("Casablanca", "Rabat", "Marrakech", "Tangier", "Fes")
        
        // When & Then
        cities.forEach { cityName ->
            val result = useCase(cityName)
            assertTrue("Failed for city: $cityName", result.isSuccess)
            assertEquals(cityName, result.getOrThrow().name)
        }
    }

    @Test
    fun `given special characters in city name when fetching then returns success`() = runTest {
        // Given
        val repo = FakeWeatherRepository()
        val useCase = GetCitySummaryUseCase(repo)
        
        // When
        val result = useCase("New York")
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals("New York", result.getOrThrow().name)
    }
}


