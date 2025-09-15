package com.babel.meteoapp

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ViewModelsTest {

    @Test
    fun `given fake repository when creating then repository is not null`() {
        // Given & When
        val repo = FakeWeatherRepository()
        
        // Then
        assertNotNull("Repository should not be null", repo)
    }

    @Test
    fun `given fake repository when setting should fail then repository has correct state`() {
        // Given
        val repo = FakeWeatherRepository()
        
        // When
        repo.shouldFail = true
        
        // Then
        assertNotNull("Repository should not be null", repo)
        assertTrue("Repository should be set to fail", repo.shouldFail)
    }
}


