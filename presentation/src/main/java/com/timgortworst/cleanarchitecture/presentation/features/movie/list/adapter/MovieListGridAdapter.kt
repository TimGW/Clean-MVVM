package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListHeaderBinding
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemNestedBinding
import com.timgortworst.cleanarchitecture.presentation.extension.getRelativeItemPosition
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.base.BaseListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup.Companion.TOTAL_COLUMNS_GRID

class MovieListGridAdapter(
    private val spanSize: Int,
    private val itemPadding: Int,
) : BaseListAdapter<Movie, MovieListItemBinding>(DiffUtilMovieItem()) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override val itemViewType = R.layout.movie_list_item

    override fun getSpanSize() = spanSize

    override fun getItemCount() = if (currentList.isEmpty()) 0 else currentList.size

    override fun getItemOffset(parent: RecyclerView, view: View): Rect? {
        val adapterPosition: Int = parent.getChildAdapterPosition(view)
        val viewType = parent.adapter?.getItemViewType(adapterPosition) ?: return null
        val position = parent.getRelativeItemPosition(adapterPosition, viewType)
        val column = position % TOTAL_COLUMNS_GRID

        return Rect().apply {
            left = itemPadding - column * itemPadding / TOTAL_COLUMNS_GRID
            right = (column + 1) * itemPadding / TOTAL_COLUMNS_GRID
            if (position < TOTAL_COLUMNS_GRID) top = itemPadding
            bottom = itemPadding
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MovieListItemBinding =
        MovieListItemBinding::inflate

    override fun bind(binding: MovieListItemBinding, item: Movie, position: Int) {
        val transName = item.highResImage + this::class.java.hashCode()

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
