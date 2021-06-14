package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.base.BaseListAdapter

class MovieListAdapter: BaseListAdapter<Movie>(DiffUtilMovieItem()) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun provideLayout() = R.layout.movie_list_item_nested

    override fun getItemCount() = if(currentList.isEmpty()) 0 else currentList.size

    override fun bind(itemView: View, item: Movie, position: Int) {
        val moviePoster = itemView.findViewById<ImageView?>(R.id.move_list_item_image)
        val transName = item.highResImage + this::class.java.hashCode()

        moviePoster?.apply {
            Glide.with(context)
                .load(item.lowResImage)
                .placeholder(R.drawable.movie_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

            transitionName = transName
        }
        moviePoster?.setOnClickListener {
            clickListener?.invoke(item, moviePoster, transName)
        }
    }
}
