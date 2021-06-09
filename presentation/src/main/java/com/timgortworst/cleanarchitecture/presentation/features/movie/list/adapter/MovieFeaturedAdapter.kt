package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListItemFeaturedBinding

class MovieFeaturedAdapter(
    private val movie: Movie
) : RecyclerView.Adapter<MovieFeaturedAdapter.ViewHolder>() {
    var clickListener: ((Movie, ImageView) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return R.layout.movie_list_item_featured
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieListItemFeaturedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movie)
    }

    inner class ViewHolder(
        binding: MovieListItemFeaturedBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val root = binding.root
        private val image: ImageView = binding.featuredImage
        private val title: TextView = binding.featuredTitle

        fun bind(movie: Movie?) {
            if (movie == null) return

            image.apply {
                Glide.with(context)
                    .load(movie.highResImage)
                    .into(this)

                transitionName = movie.highResImage
            }

            title.text = movie.title

            root.setOnClickListener {
                clickListener?.invoke(movie, image)
            }
        }
    }
}