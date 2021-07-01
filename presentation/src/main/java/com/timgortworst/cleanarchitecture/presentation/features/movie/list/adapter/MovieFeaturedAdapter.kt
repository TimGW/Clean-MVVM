package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemFeaturedBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.SpannedAdapter

class MovieFeaturedAdapter(
    movie: Movie,
    spanSize: Int,
) : SpannedAdapter<Movie, MovieListItemFeaturedBinding>(movie) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    MovieListItemFeaturedBinding = MovieListItemFeaturedBinding::inflate

    override val itemViewType = R.layout.movie_list_item_featured

    override val columnSpans = spanSize

    override fun bind(binding: MovieListItemFeaturedBinding, item: Movie, position: Int) {
        val transName = item.highResImage + getItemViewType(position)

        binding.featuredImage.apply {
            Glide.with(context)
                .load(item.highResImage)
                .into(this)

            transitionName = transName
        }

        binding.featuredTitle.text = item.title

        binding.featuredTitle.setOnClickListener {
            clickListener?.invoke(item, binding.featuredImage, transName)
        }
    }
}