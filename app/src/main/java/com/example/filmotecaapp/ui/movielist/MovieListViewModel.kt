package com.example.filmotecaapp.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmotecaapp.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor() : ViewModel() {
    private val _catalogedMovies = MutableLiveData<MutableList<Movie>>()
    val currentCatalogedMovies: LiveData<MutableList<Movie>> = _catalogedMovies

    fun insertMovieOnCatalog(movie: Movie) {
        val currentCatalogedMovieList = _catalogedMovies.value ?: mutableListOf()
        currentCatalogedMovieList.add(movie)
        _catalogedMovies.value = currentCatalogedMovieList
    }
}