package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.CastListItemBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setMargins
import com.timgortworst.cleanarchitecture.presentation.features.base.BaseListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter.GridSpanSizeLookup.Companion.FULL_WIDTH
import com.timgortworst.cleanarchitecture.presentation.model.Margins

class CastAdapter(
    spanWidth: Int,
) : BaseListAdapter<Credits.Cast, CastListItemBinding>(DiffUtilCastItem()),
    AdapterSpanSize {

    override val itemViewType = R.layout.cast_list_item

    override val columnSpans = spanWidth

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getItemCount() = if (currentList.isEmpty()) 0 else currentList.size

    private fun getItemDecoration(
        resources: Resources,
        position: Int
    ): Margins {
        val spacing = resources.getDimension(R.dimen.keyline_8).toInt()
        val maxSpanCount = FULL_WIDTH / columnSpans
        val margins = Margins()
        val column = position % maxSpanCount

        val left = spacing - column * spacing / maxSpanCount
        margins.left = if (column == 0) left * 2 else left

        val right = (column + 1) * spacing / maxSpanCount
        margins.right = if (column == maxSpanCount - 1) right * 2 else right

        if (position < maxSpanCount) margins.top = spacing
        margins.bottom = spacing

        return margins
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CastListItemBinding =
        CastListItemBinding::inflate

    override fun bind(binding: CastListItemBinding, item: Credits.Cast, position: Int) {
        with(getItemDecoration(binding.root.resources, position)) {
            binding.root.setMargins(left, top, right, bottom)
        }

        binding.moveListItemImage.apply {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185" + item.profilePath)
                .placeholder(R.drawable.media_placeholder)
                .centerCrop()
                .into(this)
        }

        binding.name.text = item.name
        binding.character.text = binding.root.context.getString(R.string.character, item.character)
    }
}