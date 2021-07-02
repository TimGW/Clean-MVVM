package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemFeaturedBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.SpannedAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.FULL_WIDTH
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.HALF_WIDTH

class MovieFeaturedAdapter(
    movie: Movie,
    spanSize: Int,
) : SpannedAdapter<Movie, MovieListItemFeaturedBinding>(movie), AdapterDecoration {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    MovieListItemFeaturedBinding = MovieListItemFeaturedBinding::inflate

    override val itemViewType = R.layout.movie_list_item_featured

    override val columnSpans = spanSize

    override fun getItemDecoration(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ): Rect {
        val spacing = resources.getDimension(R.dimen.default_padding).toInt()
        val maxSpanCount = FULL_WIDTH / columnSpans
        val outRect = Rect()
        val column = adapterPosition % maxSpanCount

        when (columnSpans) {
            FULL_WIDTH, HALF_WIDTH -> {
                // topstory & subtopstory
                if (column == 0) outRect.left = spacing
                outRect.right = spacing
                outRect.bottom = spacing
            }
            else -> {
                // break 2
                outRect.left = spacing
                outRect.right = spacing
            }
        }

        return outRect
    }

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