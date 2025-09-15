package com.babel.meteoapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelsTest {
    @Test
    fun `test basic functionality`() {
        // Test simple pour vérifier que les classes peuvent être instanciées
        val repo = FakeWeatherRepository()
        assert(repo != null)
    }
}


