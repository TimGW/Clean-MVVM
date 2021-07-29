package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import androidx.recyclerview.widget.DiffUtil
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie

class DiffUtilMovieItem : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
}