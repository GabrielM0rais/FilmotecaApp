package com.example.filmotecaapp.ui.movielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.databinding.FragmentMovieListBinding
import com.example.filmotecaapp.domain.model.Movie
import com.example.filmotecaapp.ui.movielist.adapter.MovieAdapter
import com.example.filmotecaapp.util.Constants
import com.example.filmotecaapp.util.VerticalSpaceItemDecoration
import com.example.filmotecaapp.util.getParcelableCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private val viewModel: MovieListViewModel by viewModels()

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initObservers()
        initListners()
    }

    private fun initObservers() {
        viewModel.currentCatalogedMovies.observe(viewLifecycleOwner) { movies ->
            movieAdapter.submitList(movies)
        }
    }

    private fun initListners() {
        binding.fabNavToPopularMovies.setOnClickListener {
            findNavController().navigate(R.id.action_movieListFragment_to_popoularMoviesListFragment)
        }

        parentFragmentManager.setFragmentResultListener(
            Constants.REQUEST_MOVIE_KEY,
            this
        ) { _, bundle ->
            val movie = bundle.getParcelableCompat(Constants.MOVIE_BUNDLE_KEY, Movie::class.java)
            if (movie != null) {
                viewModel.insertMovieOnCatalog(movie)
            }
        }
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter()

        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(
            verticalSpaceHeight = 8,
            firstItemTopMargin = 16,
            lastItemBottomMargin = 16
        )

        with(binding.recyclerMovieList) {
            adapter = movieAdapter
            addItemDecoration(verticalSpaceItemDecoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}