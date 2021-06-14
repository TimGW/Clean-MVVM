package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListHeaderBinding
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemFeaturedBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.base.BaseGridAdapter

class MovieFeaturedAdapter(
    movie: Movie,
    private val spanSize: Int,
    private val itemPadding: Int,
) : BaseGridAdapter<Movie, MovieListItemFeaturedBinding>(movie) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    MovieListItemFeaturedBinding = MovieListItemFeaturedBinding::inflate

    override fun getSpanSize() = spanSize

    override val itemViewType = R.layout.movie_list_item_featured

    override fun getItemOffset(parent: RecyclerView, view: View): Rect {
        return Rect().apply {
            left = itemPadding
            right = itemPadding
        }
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