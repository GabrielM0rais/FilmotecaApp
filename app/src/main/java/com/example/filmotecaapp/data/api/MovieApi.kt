package com.example.filmotecaapp.data.api

import com.example.filmotecaapp.data.model.GetPopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): GetPopularMoviesResponse
}