package com.example.filmotecaapp.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmotecaapp.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable {
    fun toMovie(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            overview = this.overview,
            release_date = this.release_date,
            poster_path = this.poster_path,
            vote_average = this.vote_average,
            favorite = this.favorite,
            user_id = this.user_Id,
        )
    }
}