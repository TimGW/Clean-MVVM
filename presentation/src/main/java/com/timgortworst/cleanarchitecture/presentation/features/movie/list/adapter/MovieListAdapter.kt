package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.SpannedListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.FULL_WIDTH

class MovieListAdapter(
    spanWidth: Int,
) : SpannedListAdapter<Movie, MovieListItemBinding>(DiffUtilMovieItem()), AdapterDecoration {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override val itemViewType = R.layout.movie_list_item

    override val columnSpans = spanWidth

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getItemCount() = if (currentList.isEmpty()) 0 else currentList.size

    override fun getItemDecoration(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ): Rect {
        val spacing = resources.getDimension(R.dimen.default_padding).toInt()
        val maxSpanCount = FULL_WIDTH / columnSpans
        val rect = Rect()
        val column = relativePosition % maxSpanCount

        rect.left = spacing - column * spacing / maxSpanCount
        rect.right = (column + 1) * spacing / maxSpanCount
        if (relativePosition < maxSpanCount) rect.top = spacing
        rect.bottom = spacing
        return rect
    }

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
