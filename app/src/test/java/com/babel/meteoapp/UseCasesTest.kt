package com.babel.meteoapp

import com.babel.meteoapp.domain.usecase.GetCitySummaryUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UseCasesTest {
    @Test
    fun `get city summary returns data`() = runTest {
        val repo = FakeWeatherRepository()
        val useCase = GetCitySummaryUseCase(repo)
        val result = useCase("Rabat").getOrThrow()
        assertEquals("Rabat", result.name)
    }
}


