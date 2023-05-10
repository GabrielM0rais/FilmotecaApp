package com.example.filmotecaapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.filmotecaapp.data.db.AppDatabase
import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.domain.model.Movie
import com.example.filmotecaapp.domain.repository.MovieDbRepository
import com.example.filmotecaapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
                val currentCatalogedMoviesList = currentCatalogedMovies.value ?: mutableListOf()
                val currentCatalogedMoviesIdList = currentCatalogedMoviesList.map { it.id }
                results.forEach {
                    if (it.id !in currentCatalogedMoviesIdList) {
                        currentPopularMoviesList.add(it)
                    }
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

    private fun insertMovieOnCatalog(movie: Movie) {
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

    fun removeMovieOfCatalog(movieId: Int) {
        viewModelScope.launch {
            try {
                _loading.postValue(true)

                movieDao.deleteMovie(movieId)
                getAllMovies()
            } catch (e: Exception) {
                println("error removing $e")
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

                val movieToEntity = movie.toMovieEntity(currentUser.value?.id!!)
                val currentPopularMoviesList = _popularMovies.value ?: mutableListOf()

                val currentPopularMoviesListFiltered = currentPopularMoviesList.filter { it.id != movie.id }

                insertMovieOnCatalog(movie)
                _catalogedMovies.postValue(currentPopularMoviesList)
                _popularMovies.postValue(currentPopularMoviesListFiltered as MutableList<Movie>?)
                movieDbRepository.saveMovie(movieToEntity)
            } catch (e: Exception) {
                println("error save on database -> ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun handleMovieOnFavorite(movie: Movie, favourite: Boolean) {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                if (currentUser.value == null) {
                    return@launch
                }

                val movieToEntity = movie.toMovieFavourite(favourite)

                movieDbRepository.saveMovie(movieToEntity)
                getAllMovies()
            } catch (e: Exception) {
                println("error handleMovieOnFavorite -> ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}