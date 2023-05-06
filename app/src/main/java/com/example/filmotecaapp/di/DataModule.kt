package com.example.filmotecaapp.di

import android.app.Application
import com.example.filmotecaapp.data.api.MovieApi
import com.example.filmotecaapp.data.db.AppDatabase
import com.example.filmotecaapp.data.db.dao.MovieDao
import com.example.filmotecaapp.data.db.dao.UserDao
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

    @Provides
    @Singleton
    fun provideUserDao(application: Application): UserDao {
        return AppDatabase.getDatabase(application).userDao()
    }

    @Provides
    @Singleton
    fun provideMovieDao(application: Application): MovieDao {
        return AppDatabase.getDatabase(application).movieDao()
    }

}