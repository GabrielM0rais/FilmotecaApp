package com.example.filmotecaapp.ui.popularmovielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.filmotecaapp.databinding.FragmentPopoularMoviesBinding
import com.example.filmotecaapp.ui.popularmovielist.adapter.PopularMoviesAdapter
import com.example.filmotecaapp.util.Constants
import com.example.filmotecaapp.util.StateView
import com.example.filmotecaapp.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopoularMoviesListFragment : Fragment() {

    companion object {
        fun newInstance() = PopoularMoviesListFragment()
    }

    private val viewModel: PopularMoviesListViewModel by viewModels()

    private var _binding: FragmentPopoularMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

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


        initRecycler()
        initObservers()
        getPopularMovies(1)
    }

    private fun getPopularMovies(page: Int) {
        viewModel.getPopularMovies(page).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Success -> {

//                    parentFragmentManager.setFragmentResult(
//                        Constants.REQUEST_MOVIE_KEY,
//                        bundleOf(Pair(Constants.MOVIE_BUNDLE_KEY, stateView.data?.get(0)))
//                    )

                }
                is StateView.Error<*> -> {
                    println("ERROR")
                }
                else -> {}
            }
        }
    }

    private fun initObservers() {
        viewModel.currentPopularMovies.observe(viewLifecycleOwner) { popularMovies ->
            popularMoviesAdapter.submitList(popularMovies)
        }
    }

    private fun initRecycler() {
        popularMoviesAdapter = PopularMoviesAdapter()

        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(
            verticalSpaceHeight = 8,
            firstItemTopMargin = 16,
            lastItemBottomMargin = 16
        )

        with(binding.recyclerMovieList) {
            adapter = popularMoviesAdapter
            addItemDecoration(verticalSpaceItemDecoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}