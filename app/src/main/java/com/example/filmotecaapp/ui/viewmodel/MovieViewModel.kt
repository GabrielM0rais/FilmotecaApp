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
    val currentUser = _user


    fun insertMovieOnCatalog(movie: Movie) {
        viewModelScope.launch {
            try {
                val currentCatalogedMovieList = _catalogedMovies.value ?: mutableListOf()
                currentCatalogedMovieList.add(movie)
                _catalogedMovies.postValue(currentCatalogedMovieList)
            } catch (e: Exception) {
                println("error inserting $e")
            }
        }
    }

    fun setUser(user: User) {
        _user.value = user
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                currentPage += 1

                val movies = repository.getPopularMovies(currentPage)
                val results = movies.results
                val currentPopularMoviesList = _popularMovies.value ?: mutableListOf()
                results.forEach {
                    currentPopularMoviesList.add(it)
                }
                _popularMovies.postValue(currentPopularMoviesList)
            } catch (e: Exception) {
                println(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun getAllMovies() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                if (currentUser.value == null) {
                    return@launch
                }

                println("currentUser.value?.id!! ${currentUser.value?.id!!}")

                val results = movieDao.getAllMovies(currentUser.value?.id!!)

                val currentCatalogedMovieList: MutableList<Movie> = mutableListOf()
                results.forEach {
                    currentCatalogedMovieList.add(it.toMovie())
                }

                _catalogedMovies.postValue(currentCatalogedMovieList)
            } catch (e: Exception) {
                println("error all movies -> ${e}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun setMovieOnDatabase(movie: Movie) {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                if (currentUser.value == null) {
                    return@launch
                }

                println("currentUser.value?.id!! ${currentUser.value?.id!!}")

                val movieToEntity = movie.toMovieEntity(currentUser.value?.id!!)

                println("movieToEntity ${movieToEntity}")

                insertMovieOnCatalog(movie)
                movieDbRepository.saveMovie(movieToEntity)
            } catch (e: Exception) {
                println("error save on database -> ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
