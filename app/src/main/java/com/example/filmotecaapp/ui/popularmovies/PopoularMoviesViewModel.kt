package com.example.filmotecaapp.ui.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.filmotecaapp.domain.usecase.GetPopularMoviesUseCase
import com.example.filmotecaapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PopoularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    fun getPopularMovies(page: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movies = getPopularMoviesUseCase(page)

            emit(StateView.Success(movies))
        }
        catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }
}