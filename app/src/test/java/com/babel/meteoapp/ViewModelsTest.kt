package com.babel.meteoapp

import com.babel.meteoapp.domain.usecase.GetCitySummaryUseCase
import com.babel.meteoapp.domain.usecase.GetForecastByCityUseCase
import com.babel.meteoapp.viewmodel.CityListViewModel
import com.babel.meteoapp.viewmodel.DetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelsTest {
    @Test
    fun `city list loads defaults`() = runTest(UnconfinedTestDispatcher()) {
        val repo = FakeWeatherRepository()
        val vm = CityListViewModel(GetCitySummaryUseCase(repo))
        advanceUntilIdle()
        val list = vm.filteredSummaries.value
        // defaults = 5
        assertEquals(5, list.size)
    }

    @Test
    fun `detail loads by city`() = runTest(UnconfinedTestDispatcher()) {
        val repo = FakeWeatherRepository()
        val vm = DetailViewModel(GetForecastByCityUseCase(repo))
        vm.loadByCity("Fes")
        advanceUntilIdle()
        assertEquals("Fes", vm.details.value?.cityName)
    }
}


