package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.CastListItemBinding
import com.timgortworst.cleanarchitecture.presentation.features.base.BaseListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter.GridSpanSizeLookup.Companion.FULL_WIDTH

class CastAdapter(
    spanWidth: Int,
) : BaseListAdapter<Credits.Cast, CastListItemBinding>(DiffUtilCastItem()),
    AdapterSpanSize,
    AdapterDecoration {

    override val itemViewType = R.layout.cast_list_item

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
        val spacing = resources.getDimension(R.dimen.keyline_8).toInt()
        val maxSpanCount = FULL_WIDTH / columnSpans
        val rect = Rect()
        val column = relativePosition % maxSpanCount

        val left = spacing - column * spacing / maxSpanCount
        rect.left = if (column == 0) left * 2 else left

        val right = (column + 1) * spacing / maxSpanCount
        rect.right = if (column == maxSpanCount - 1) right * 2 else right

        if (relativePosition < maxSpanCount) rect.top = spacing
        rect.bottom = spacing
        return rect
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CastListItemBinding =
        CastListItemBinding::inflate

    override fun bind(binding: CastListItemBinding, item: Credits.Cast, position: Int) {
        binding.moveListItemImage.apply {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185" + item.profilePath)
                .placeholder(R.drawable.media_placeholder)
                .centerCrop()
                .into(this)
        }

        binding.name.text = item.name

        val character = binding.root.context.getString(R.string.character, item.character)
        binding.character.text = character
    }
}