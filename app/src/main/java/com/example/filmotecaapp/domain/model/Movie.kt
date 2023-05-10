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
            id = this.id,
            title = this.title,
            overview = this.overview,
            release_date = this.release_date,
            poster_path = this.poster_path,
            vote_average = this.vote_average,
            favorite = this.favorite,
            user_Id = userId
        )
    }
}