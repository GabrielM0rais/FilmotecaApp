package com.example.filmotecaapp.ui.popularmovielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmotecaapp.databinding.FragmentPopoularMoviesBinding
import com.example.filmotecaapp.ui.popularmovielist.adapter.PopularMoviesAdapter
import com.example.filmotecaapp.ui.viewmodel.MovieViewModel
import com.example.filmotecaapp.util.Constants
import com.example.filmotecaapp.util.StateView
import com.example.filmotecaapp.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopoularMoviesListFragment : Fragment() {

    companion object {
        fun newInstance() = PopoularMoviesListFragment()
    }

    private val viewModel: MovieViewModel by viewModels()

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
        initListeners()
        getPopularMovies()
    }

    private fun initObservers() {
        viewModel.currentPopularMovies.observe(viewLifecycleOwner) { popularMovies ->
            popularMoviesAdapter.submitList(popularMovies)
        }
    }

    private fun initListeners() {
        binding.recyclerMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val debounceTime = 1000L
            private var lastTime = 0L

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (totalItemCount - lastVisibleItem <= 1) {
                    val now = System.currentTimeMillis()
                    if (now - lastTime > debounceTime) {
                        lastTime = now
                        getPopularMovies()
                    }
                }
            }
        })

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

    private fun getPopularMovies() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    popularMoviesAdapter.notifyDataSetChanged()
//                    parentFragmentManager.setFragmentResult(
//                        Constants.REQUEST_MOVIE_KEY,
//                        bundleOf(Pair(Constants.MOVIE_BUNDLE_KEY, stateView.data?.get(0)))
//                    )
                }
                is StateView.Error -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}