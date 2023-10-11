package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemFeaturedBinding
import com.timgortworst.cleanarchitecture.presentation.extension.doNothing
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.SpannedAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.Spans
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.Spans.Companion.calculateMaxSpanCount
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.decoration.AdapterDecoration

class MovieFeaturedAdapter(
    movie: Movie,
    override val spans: Spans,
) : SpannedAdapter<Movie, MovieListItemFeaturedBinding>(movie), AdapterDecoration {
    var clickListener: ((Movie, ImageView) -> Unit)? = null

    override val bindingInflater: BindingInflater<MovieListItemFeaturedBinding>
        get() = MovieListItemFeaturedBinding::inflate

    override val itemViewType = R.layout.movie_list_item_featured

    override fun getItemRect(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ): Rect {
        val spacing = resources.getDimension(R.dimen.default_padding).toInt()
        val outRect = Rect()
        val column = adapterPosition % calculateMaxSpanCount(spans)

        when (spans) {
            Spans.FullWidth, Spans.HalfWidth -> {
                // topstory & subtopstory
                if (column == 0) outRect.left = spacing
                outRect.right = spacing
                outRect.bottom = spacing
            }

            is Spans.Relative -> {
                // break 2
                outRect.left = spacing
                outRect.right = spacing
            }

            else -> doNothing
        }

        return outRect
    }

    override fun bind(binding: MovieListItemFeaturedBinding, item: Movie, position: Int) {
        binding.featuredImage.apply {
            Glide.with(context)
                .load(item.lowResImage)
                .into(this)
        }

        binding.featuredTitle.text = item.title

        binding.featuredTitle.setOnClickListener {
            clickListener?.invoke(item, binding.featuredImage)
        }
    }
}