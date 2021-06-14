package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.base.BaseGridAdapter

class MovieFeaturedAdapter(
    movie: Movie,
    private val spanSize: Int,
    private val itemPadding: Int,
) : BaseGridAdapter<Movie>(movie) {
    var clickListener: ((Movie, ImageView, String) -> Unit)? = null

    override fun provideLayout() = R.layout.movie_list_item_featured

    override fun getSpanSize() = spanSize

    override fun getItemOffset(parent: RecyclerView, view: View): Rect {
        return Rect().apply {
            left = itemPadding
            right = itemPadding
        }
    }

    override fun bind(itemView: View, item: Movie, position: Int) {
        val image = itemView.findViewById<ImageView?>(R.id.featured_image)
        val title = itemView.findViewById<TextView?>(R.id.featured_title)
        val transName = item.highResImage + this::class.java.hashCode()

        image?.apply {
            Glide.with(context)
                .load(item.highResImage)
                .into(this)

            transitionName = transName
        }

        title?.text = item.title

        itemView.setOnClickListener {
            clickListener?.invoke(item, image, transName)
        }
    }
}