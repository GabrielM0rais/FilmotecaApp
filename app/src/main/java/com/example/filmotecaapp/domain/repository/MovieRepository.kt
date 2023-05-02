package com.example.filmotecaapp.domain.repository

import com.example.filmotecaapp.data.model.GetPopularMoviesResponse

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): GetPopularMoviesResponse
}