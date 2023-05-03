package com.example.filmotecaapp.ui.popularmovielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.filmotecaapp.domain.usecase.GetPopularMoviesUseCase
import com.example.filmotecaapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PopularMoviesListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    fun getPopularMovies(page: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movies = getPopularMoviesUseCase(page)

            emit(StateView.Success(movies.results))
        }
        catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }
}