package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemNestedBinding

class MovieListAdapter: ListAdapter<Movie, MovieListAdapter.ViewHolder>(DiffUtilMovieItem()) {
    var clickListener: ((Movie, ImageView) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.movie_list_item_nested
    }

    override fun getItemCount() = if(currentList.isEmpty()) 0 else currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListItemNestedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffUtilMovieItem : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    inner class ViewHolder(
        binding: MovieListItemNestedBinding
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
