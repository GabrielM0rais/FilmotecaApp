package com.example.filmotecaapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.filmotecaapp.data.db.AppDatabase
import com.example.filmotecaapp.data.db.MovieEntity
import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.domain.model.Movie
import com.example.filmotecaapp.domain.repository.MovieDbRepository
import com.example.filmotecaapp.domain.repository.MovieRepository
import com.example.filmotecaapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val database: AppDatabase,
    private val movieDbRepository: MovieDbRepository
) : ViewModel() {

    private val movieDao = database.movieDao()

    var currentPage: Int = 0

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _popularMovies = MutableLiveData<MutableList<Movie>>()
    val currentPopularMovies: LiveData<MutableList<Movie>> = _popularMovies

    private val _catalogedMovies = MutableLiveData<MutableList<Movie>>()
    val currentCatalogedMovies: LiveData<MutableList<Movie>> = _catalogedMovies

    private val _user = MutableLiveData<User>()
    val currentUser: LiveData<User> = _user

    fun insertMovieOnCatalog(movie: Movie) {
        val currentCatalogedMovieList = _catalogedMovies.value ?: mutableListOf()
        currentCatalogedMovieList.add(movie)
        _catalogedMovies.value = currentCatalogedMovieList
    }

    fun setUser(user: User) {
        _user.value = user
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

    fun setMovieOnDatabase(movie: Movie) {
        viewModelScope.launch {
            try {
                _loading.postValue(true)

                val newMovie = movie.toMovieEntity(user_id = currentUser.value?.id ?: 0)
                val movieWithUserId = movie.toMovieWithUserId(user_id = currentUser.value?.id ?: 0)

                insertMovieOnCatalog(movieWithUserId)

                movieDbRepository.saveMovie(newMovie)
            } catch (e: Exception) {
                println(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
