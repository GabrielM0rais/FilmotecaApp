package com.example.filmotecaapp.domain.datasource

import com.example.filmotecaapp.data.db.MovieEntity
import com.example.filmotecaapp.data.db.dao.MovieDao
import com.example.filmotecaapp.domain.model.Movie
import com.example.filmotecaapp.domain.repository.MovieDbRepository
import javax.inject.Inject

class MovieDbDataSource @Inject constructor(
    private val movieDao: MovieDao
): MovieDbRepository {
    override suspend fun saveMovie(movie: MovieEntity) {
        return movieDao.saveMovie(movie)
    }

    override suspend fun getMovie(id: Int): MovieEntity {
        return movieDao.getMovie(id)
    }

    override suspend fun getAllMovies(user_id: Long): List<MovieEntity> {
        return movieDao.getAllMovies(user_id)
    }

}