package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.content.res.Resources
import androidx.recyclerview.widget.ConcatAdapter
import com.timgortworst.cleanarchitecture.presentation.R

class MovieListSpanSizeLookup(
    resources: Resources,
    private val adapter: ConcatAdapter,
) : MaxSizeLookup() {
    val spanSize = resources.getInteger(R.integer.gallery_columns)

    override fun getSpanSize(position: Int): Int {
        val type = adapter.getItemViewType(position)
        return if (
            type == R.layout.movie_list_header ||
            type == R.layout.movie_list_item_featured ||
            type == R.layout.movie_list_nested
        ) {
            spanSize // take full width
        } else {
            1
        }
    }

    override fun getMaxSpanSize(): Int {
        return spanSize
    }
}