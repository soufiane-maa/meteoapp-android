package com.babel.meteoapp.di

import com.babel.meteoapp.BuildConfig
import com.babel.meteoapp.data.WeatherRepositoryImpl
import com.babel.meteoapp.domain.repository.WeatherRepository
import com.babel.meteoapp.domain.usecase.GetCitySummaryUseCase
import com.babel.meteoapp.domain.usecase.GetForecastByCityUseCase
import com.babel.meteoapp.domain.usecase.GetForecastByCoordsUseCase
import com.babel.meteoapp.network.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofitProvider(@ApplicationContext context: Context): RetrofitProvider = RetrofitProvider(context)

    @Provides
    @Singleton
    fun provideWeatherRepository(retrofitProvider: RetrofitProvider): WeatherRepository = WeatherRepositoryImpl({ BuildConfig.OPENWEATHER_API_KEY }, retrofitProvider)

    @Provides
    fun provideGetCitySummaryUseCase(repo: WeatherRepository) = GetCitySummaryUseCase(repo)

    @Provides
    fun provideGetForecastByCityUseCase(repo: WeatherRepository) = GetForecastByCityUseCase(repo)

    @Provides
    fun provideGetForecastByCoordsUseCase(repo: WeatherRepository) = GetForecastByCoordsUseCase(repo)
}


