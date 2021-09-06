package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.AdapterRelatedMovieBinding
import com.timgortworst.cleanarchitecture.presentation.features.base.BaseListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.DiffUtilMovieItem

class RelatedMoviesAdapter :
    BaseListAdapter<Movie, AdapterRelatedMovieBinding>(DiffUtilMovieItem()) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override val itemViewType = R.layout.media_list_item

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterRelatedMovieBinding =
        AdapterRelatedMovieBinding::inflate

    override fun bind(binding: AdapterRelatedMovieBinding, item: Movie, position: Int) {
        val highResImage = Movie.HIGH_RES_PREFIX + item.posterPath
        val lowResImage = Movie.LOW_RES_PREFIX + item.posterPath
        val transName = highResImage + getItemViewType(position)

        binding.moveListItemImage.apply {
            Glide.with(context)
                .load(lowResImage)
                .placeholder(R.drawable.media_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

            transitionName = transName
        }
        binding.moveListItemImage.setOnClickListener {
            clickListener?.invoke(item, binding.moveListItemImage, transName)
        }
    }
}
