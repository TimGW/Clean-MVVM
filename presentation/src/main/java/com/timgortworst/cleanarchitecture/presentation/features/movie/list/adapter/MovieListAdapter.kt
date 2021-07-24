package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemNestedBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.BaseListAdapter

class MovieListAdapter: BaseListAdapter<Movie, MovieListItemNestedBinding>(DiffUtilMovieItem()) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override val itemViewType = R.layout.movie_list_item_nested

    override fun getItemCount() = if(currentList.isEmpty()) 0 else currentList.size

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MovieListItemNestedBinding =
        MovieListItemNestedBinding::inflate

    override fun bind(binding: MovieListItemNestedBinding, item: Movie, position: Int) {
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
