package com.example.filmotecaapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.filmotecaapp.domain.model.Movie
import com.example.filmotecaapp.domain.repository.MovieRepository
import com.example.filmotecaapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel  @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _popularMovies = MutableLiveData<MutableList<Movie>>()
    val currentPopularMovies: LiveData<MutableList<Movie>> = _popularMovies
    var currentPage: Int = 0

    private val _catalogedMovies = MutableLiveData<MutableList<Movie>>()
    val currentCatalogedMovies: LiveData<MutableList<Movie>> = _catalogedMovies

    fun insertMovieOnCatalog(movie: Movie) {
        val currentCatalogedMovieList = _catalogedMovies.value ?: mutableListOf()
        currentCatalogedMovieList.add(movie)
        _catalogedMovies.value = currentCatalogedMovieList
    }

    fun getPopularMovies() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            currentPage += 1

            val movies = repository.getPopularMovies(currentPage)
            val results = movies.results
            val currentPopularMoviesList = _popularMovies.value ?: mutableListOf()
            results.forEach {
                currentPopularMoviesList.add(it)
            }
            _popularMovies.postValue(currentPopularMoviesList)

            emit(StateView.Success(movies.results))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }
}
