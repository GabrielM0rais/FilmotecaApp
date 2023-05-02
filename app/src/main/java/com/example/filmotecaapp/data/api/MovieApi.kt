package com.example.filmotecaapp.data.api

import com.example.filmotecaapp.data.model.GetPopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/popular?api_key={api_key}&language=en-US&page={page}")
    suspend fun getPopularMovies(
        @Path("page") page: Int,
        @Path("api_key") apiKey: String
    ): GetPopularMoviesResponse
}