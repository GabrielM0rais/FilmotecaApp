package com.example.filmotecaapp.domain.model

import android.os.Parcelable
import com.example.filmotecaapp.data.db.MovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String,
    val vote_average: Double,
    val user_id: Long,
    val favorite: Boolean
): Parcelable {
    fun getImageSource(): String {
        return "https://image.tmdb.org/t/p/original${poster_path}"
    }

    fun toMovieEntity(userId: Long): MovieEntity{
        return MovieEntity(
            id = id,
            title = title,
            overview = overview,
            release_date = release_date,
            poster_path = poster_path,
            vote_average = vote_average,
            favorite = false,
            user_Id = userId
        )
    }
}