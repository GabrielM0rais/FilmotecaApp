package com.example.filmotecaapp.di

import com.example.filmotecaapp.data.repository.MovieRepositoryImpl
import com.example.filmotecaapp.domain.datasource.MovieDbDataSource
import com.example.filmotecaapp.domain.datasource.UserDbDataSource
import com.example.filmotecaapp.domain.repository.MovieDbRepository
import com.example.filmotecaapp.domain.repository.MovieRepository
import com.example.filmotecaapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindMovieRepositoryImpl (
        movieRepositoryImpl: MovieRepositoryImpl
    ) : MovieRepository

    @Binds
    abstract fun bindUserDbDataSource (
        userDbDataSource: UserDbDataSource
    ) : UserRepository

    @Binds
    abstract fun bindMovieDbDataSource (
        movieDbDataSource: MovieDbDataSource
    ) : MovieDbRepository

}