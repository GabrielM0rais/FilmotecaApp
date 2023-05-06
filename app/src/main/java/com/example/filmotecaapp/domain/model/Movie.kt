package com.example.filmotecaapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double
): Parcelable {
    fun getImageSource(): String {
        return "https://image.tmdb.org/t/p/original${poster_path}"
    }
}