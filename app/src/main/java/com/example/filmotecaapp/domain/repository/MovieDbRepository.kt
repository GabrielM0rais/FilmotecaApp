package com.example.filmotecaapp.domain.repository

import androidx.room.Query
import com.example.filmotecaapp.data.db.MovieEntity

interface MovieDbRepository {
    suspend fun saveMovie(movie: MovieEntity)

    suspend fun getMovie(id: Int): MovieEntity

    suspend fun getAllMovies(user_id: Long): List<MovieEntity>
}