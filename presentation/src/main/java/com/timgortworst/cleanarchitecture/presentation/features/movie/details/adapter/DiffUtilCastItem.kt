package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import androidx.recyclerview.widget.DiffUtil
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie

class DiffUtilCastItem : DiffUtil.ItemCallback<Credits.Cast>() {
    override fun areItemsTheSame(oldItem: Credits.Cast, newItem: Credits.Cast) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Credits.Cast, newItem: Credits.Cast) = oldItem == newItem
}