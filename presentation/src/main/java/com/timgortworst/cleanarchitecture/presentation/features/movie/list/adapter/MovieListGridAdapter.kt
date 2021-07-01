package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.SpannedListAdapter

class MovieListGridAdapter(
    spanWidth: Int,
) : SpannedListAdapter<Movie, MovieListItemBinding>(DiffUtilMovieItem()) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override val itemViewType = R.layout.movie_list_item

    override val columnSpans = spanWidth

    override fun getItemCount() = if (currentList.isEmpty()) 0 else currentList.size

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MovieListItemBinding =
        MovieListItemBinding::inflate

    override fun bind(binding: MovieListItemBinding, item: Movie, position: Int) {
        val transName = item.highResImage + getItemViewType(position)

        binding.moveListItemImage.apply {
            Glide.with(context)
                .load(item.lowResImage)
                .placeholder(R.drawable.movie_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

            transitionName = transName
        }
        binding.moveListItemImage.setOnClickListener {
            clickListener?.invoke(item, binding.moveListItemImage, transName)
        }
    }
}
