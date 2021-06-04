package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding

class MovieListAdapter(
    private var clickListener: ((Movie, ImageView) -> Unit)? = null
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val movieList = mutableListOf<Movie>()

    override fun getItemCount() = movieList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bindMovie(movie)
    }

    inner class ViewHolder(
        binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val moviePoster: ImageView = binding.moveListItemImage

        fun bindMovie(movie: Movie) {
            moviePoster.apply {
                Glide.with(context)
                    .load(movie.highResImage)
                    .placeholder(R.drawable.movie_placeholder)
                    .into(this)

                transitionName = movie.highResImage
            }
            moviePoster.setOnClickListener {
                clickListener?.invoke(movieList[bindingAdapterPosition], moviePoster)
            }
        }
    }

    fun addMoviesToList(movieList: MutableList<Movie>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }
}
