package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemBinding
import com.timgortworst.cleanarchitecture.presentation.extension.getRelativeItemPosition
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterSpanSize
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup.Companion.TOTAL_COLUMNS_GRID

class MovieListGridAdapter(
    private val spanSize: Int,
    private val itemPadding: Int,
) : ListAdapter<Movie, MovieListGridAdapter.ViewHolder>(DiffUtilMovieItem()),
    AdapterSpanSize,
    AdapterDecoration {
    var clickListener: ((Movie, ImageView) -> Unit)? = null

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.movie_list_item
    }

    override fun getSpanSize() = spanSize

    override fun getItemOffset(parent: RecyclerView, view: View): Rect? {
        val adapterPosition: Int = parent.getChildAdapterPosition(view)
        val viewType = parent.adapter?.getItemViewType(adapterPosition) ?: return null

        return Rect().apply {
            addGridMargin(parent, adapterPosition, viewType)
        }
    }

    override fun getItemCount() = if (currentList.isEmpty()) 0 else currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun Rect.addGridMargin(
        parent: RecyclerView,
        adapterPosition: Int,
        viewType: Int
    ) {
        val position = parent.getRelativeItemPosition(adapterPosition, viewType)
        val column = position % TOTAL_COLUMNS_GRID

        left = itemPadding - column * itemPadding / TOTAL_COLUMNS_GRID
        right = (column + 1) * itemPadding / TOTAL_COLUMNS_GRID
        if (position < TOTAL_COLUMNS_GRID) top = itemPadding
        bottom = itemPadding
    }

    inner class ViewHolder(
        binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val moviePoster: ImageView = binding.moveListItemImage

        fun bind(movie: Movie) {
            moviePoster.apply {
                Glide.with(context)
                    .load(movie.lowResImage)
                    .placeholder(R.drawable.movie_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(this)

                transitionName = movie.highResImage
            }
            moviePoster.setOnClickListener {
                clickListener?.invoke(movie, moviePoster)
            }
        }
    }
}
