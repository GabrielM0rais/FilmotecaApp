package com.example.filmotecaapp.domain.usecase

import com.example.filmotecaapp.data.model.GetPopularMoviesResponse
import com.example.filmotecaapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): GetPopularMoviesResponse {
        return repository.getPopularMovies(page)
    }
}