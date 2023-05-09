package com.example.filmotecaapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity (
    @PrimaryKey val id: Int,
    val user_Id: Long,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String,
    val vote_average: Double,
    val favorite: Boolean
)