package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.SpannedListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.Spans
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.decoration.AdapterDecoration

class MovieListAdapter(
    override val spans: Spans,
    private val decoration: AdapterDecoration? = null,
) : SpannedListAdapter<Movie, MovieListItemBinding>(DiffUtilMovieItem()), AdapterDecoration {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MovieListItemBinding =
        MovieListItemBinding::inflate

    override val itemViewType = R.layout.movie_list_item

    override fun getItemRect(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ) = decoration?.getItemRect(resources, adapterPosition, relativePosition) ?: run {
        val spacing = resources.getDimension(R.dimen.default_padding).toInt()
        val maxSpanCount = Spans.calculateMaxSpanCount(spans)
        val column = relativePosition % maxSpanCount
        return Rect().apply {
            left = spacing - column * spacing / maxSpanCount
            right = (column + 1) * spacing / maxSpanCount
            if (relativePosition < maxSpanCount) top = spacing
            bottom = spacing
        }
    }

    override fun bind(binding: MovieListItemBinding, item: Movie, position: Int) {
        val transName = item.highResImage + getItemViewType(position)

        binding.moveListItemText?.text = item.title

        binding.moveListItemImage.apply {
            Glide.with(context)
                .load(item.highResImage)
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
