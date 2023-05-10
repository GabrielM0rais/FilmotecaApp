package com.example.filmotecaapp.ui.movielist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.R
import com.example.filmotecaapp.data.db.AppDatabase
import com.example.filmotecaapp.data.model.User
import com.example.filmotecaapp.databinding.FragmentMovieListBinding
import com.example.filmotecaapp.ui.movielist.adapter.MovieAdapter
import com.example.filmotecaapp.ui.viewmodel.MovieViewModel
import com.example.filmotecaapp.util.VerticalSpaceItemDecoration
import com.example.filmotecaapp.util.getParcelableCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private lateinit var viewModel: MovieViewModel

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initObservers()
        initListners()

    }

    private fun initObservers() {
        viewModel.getAllMovies()

        viewModel.currentCatalogedMovies.observe(viewLifecycleOwner) { popularMovies ->
            movieAdapter.submitList(popularMovies)
        }
    }

    private fun initListners() {
        binding.fabNavToPopularMovies.setOnClickListener {
            findNavController().navigate(R.id.action_movieListFragment_to_popoularMoviesListFragment)
        }

        parentFragmentManager.setFragmentResultListener(
            "GetUser",
            this
        ) { _, bundle ->
            val user = bundle.getParcelableCompat("User", User::class.java)
            if (user != null) {
                viewModel.setUser(user)
                viewModel.getAllMovies()

            }
        }
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter(viewModel)

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