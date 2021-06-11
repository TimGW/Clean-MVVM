package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding

class MovieListGridAdapter :
    ListAdapter<Movie, MovieListGridAdapter.ViewHolder>(DiffUtilMovieItem()) {
    var clickListener: ((Movie, ImageView) -> Unit)? = null

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.movie_list_item
    }

    override fun getItemCount() = if (currentList.isEmpty()) 0 else currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val moviePoster: ImageView = binding.moveListItemImage

        fun bind(movie: Movie) {
            moviePoster.apply {
                Glide.with(context)
                    .load(movie.lowResImage)
                    .placeholder(R.drawable.movie_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(this)

                transitionName = movie.highResImage
            }
            moviePoster.setOnClickListener {
                clickListener?.invoke(movie, moviePoster)
            }
        }
    }
}
