package com.example.filmotecaapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity (
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val user_Id: Long,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: Double,
    val vote_count: Int,
    val adult: Boolean,
    val favorite: Boolean
)