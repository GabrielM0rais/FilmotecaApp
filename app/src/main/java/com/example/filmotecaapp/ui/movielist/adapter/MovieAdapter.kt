package com.example.filmotecaapp.ui.movielist.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filmotecaapp.R
import com.example.filmotecaapp.databinding.MovieListItemBinding
import com.example.filmotecaapp.domain.model.Movie
import com.example.filmotecaapp.ui.viewmodel.MovieViewModel
import com.squareup.picasso.Picasso

class MovieAdapter(private val viewModel: MovieViewModel) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

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

        Picasso.get().load(movie.getImageSource()).placeholder(R.drawable.filmoteca_logo)
            .error(R.drawable.filmoteca_logo).into(holder.binding.MovieImage)
        holder.binding.MovieTitle.text = movie.title
        holder.binding.RateText.text = movie.vote_average.toString()
        holder.binding.ReleaseDate.text = movie.release_date

        if (movie.favorite) {
            holder.binding.viewFlipper.displayedChild = 2
        } else if (!movie.favorite) {
            holder.binding.viewFlipper.displayedChild = 1
        }

        holder.binding.buttonAddMovieToFavourite.setOnClickListener {
            viewModel.handleMovieOnFavorite(movie, true)
        }

        holder.binding.buttonRemoveMovieToMyList.setOnClickListener {
            viewModel.handleMovieOnFavorite(movie, false)
        }

        holder.binding.movieCardView.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Removendo filme")
                .setMessage(
                    "Tem certeza que deseja retirar do seu catalogo de filme?"
                )
                .setPositiveButton("OK") { _, _ ->
                    viewModel.removeMovieOfCatalog(movie.id)
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()

            true
        }
    }

    inner class ViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}