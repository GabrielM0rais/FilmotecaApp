package com.example.filmotecaapp.di

import com.example.filmotecaapp.data.api.MovieApi
import com.example.filmotecaapp.network.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun providesServiceApi(
        serviceProvider: ServiceProvider
    ): MovieApi {
        return serviceProvider.createService(MovieApi::class.java)
    }

}