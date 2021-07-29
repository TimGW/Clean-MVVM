package com.timgortworst.cleanarchitecture.presentation.features.tv.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MediaListItemBinding
import com.timgortworst.cleanarchitecture.presentation.features.base.BasePagingAdapter

class TvShowsAdapter : BasePagingAdapter<TvShow, MediaListItemBinding>(DiffUtilTvShowItem()) {
    var clickListener: ((TvShow, ImageView, String) -> Unit)? = null

    override val itemViewType = R.layout.media_list_item

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MediaListItemBinding =
        MediaListItemBinding::inflate

    override fun bind(binding: MediaListItemBinding, item: TvShow, position: Int) {
        val transName = item.highResImage + getItemViewType(position)

        binding.moveListItemImage.apply {
            Glide.with(context)
                .load(item.lowResImage)
                .placeholder(R.drawable.media_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

            transitionName = transName
        }
        binding.moveListItemImage.setOnClickListener {
            clickListener?.invoke(item, binding.moveListItemImage, transName)
        }
    }
}
