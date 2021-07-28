package com.timgortworst.cleanarchitecture.presentation.features.tv.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow

class DiffUtilTvShowItem : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) = oldItem == newItem
}