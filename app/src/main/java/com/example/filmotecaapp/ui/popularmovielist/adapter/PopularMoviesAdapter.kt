package com.example.filmotecaapp.ui.popularmovielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.filmotecaapp.R
import com.example.filmotecaapp.databinding.MovieListItemBinding
import com.example.filmotecaapp.domain.model.Movie
import com.squareup.picasso.Picasso

class PopularMoviesAdapter : ListAdapter<Movie, PopularMoviesAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

//        holder.binding.textViewMovie.text = movie.title

        Picasso.get().load(movie.getImageSource()).placeholder(R.drawable.filmoteca_logo)
            .error(R.drawable.filmoteca_logo).into(holder.binding.MovieImage)
        holder.binding.MovieTitle.text = movie.title
        holder.binding.RateText.text = movie.vote_average.toString()
        holder.binding.ReleaseDate.text = movie.release_date
    }

    inner class ViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}