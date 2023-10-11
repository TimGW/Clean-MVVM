package com.timgortworst.cleanarchitecture.presentation.features.movie.list.nested

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemNestedBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.DiffUtilMovieItem

class NestedMovieListAdapter : ListAdapter<Movie, NestedMovieListAdapter.ViewHolder>(
    DiffUtilMovieItem()
) {
    var clickListener: ((Movie, ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = R.layout.movie_list_item_nested

    inner class ViewHolder(
        private val binding: MovieListItemNestedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.moveListItemImage.apply {
                Glide.with(context)
                    .load(movie.lowResImage)
                    .placeholder(R.drawable.movie_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(this)
            }
            binding.moveListItemImage.setOnClickListener {
                clickListener?.invoke(movie, binding.moveListItemImage)
            }
        }
    }
}
