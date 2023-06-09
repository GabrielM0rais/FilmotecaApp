package com.example.filmotecaapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmotecaapp.data.db.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieEntity)

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovie(id: Int): MovieEntity

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovie(id: Int)

    @Query("SELECT * FROM movie where user_id = :user_id ORDER BY favorite DESC, title ASC")
    suspend fun getAllMovies(user_id: Long): List<MovieEntity>
}