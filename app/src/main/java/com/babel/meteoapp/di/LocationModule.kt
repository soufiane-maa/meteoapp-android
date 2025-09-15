package com.babel.meteoapp.di

import android.content.Context
import com.babel.meteoapp.data.LocationProviderImpl
import com.babel.meteoapp.domain.location.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider = LocationProviderImpl(context)
}


