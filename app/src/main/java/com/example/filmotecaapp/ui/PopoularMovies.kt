package com.example.filmotecaapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.filmotecaapp.databinding.FragmentPopoularMoviesBinding
import com.example.filmotecaapp.util.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopoularMovies : Fragment() {

    companion object {
        fun newInstance() = PopoularMovies()
    }

    private val viewModel: MovieViewModel by viewModels()

    private var _binding: FragmentPopoularMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopoularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPopularMovies(1)
    }

    private fun getPopularMovies(page: Int) {
        viewModel.getPopularMovies(page).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Success -> {
                    println(stateView.data?.get(0)?.title)
                    binding.textviewText.text = stateView.data?.get(0)?.title
                }
                is StateView.Error<*> -> {
                    println("ERROR")
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}