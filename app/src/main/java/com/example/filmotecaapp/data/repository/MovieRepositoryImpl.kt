package com.example.filmotecaapp.data.repository

import com.example.filmotecaapp.data.api.MovieApi
import com.example.filmotecaapp.data.model.GetPopularMoviesResponse
import com.example.filmotecaapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieApi
): MovieRepository  {
    companion object {
        const val API_KEY = "8849422d79bf2cc5aa0b9541afffb658"
    }

    override suspend fun getPopularMovies(page: Int): GetPopularMoviesResponse {
        return service.getPopularMovies(API_KEY, "en", page)
    }
}