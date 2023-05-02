package com.example.filmotecaapp.data.model

import com.example.filmotecaapp.domain.model.Movie

data class GetPopularMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
)
