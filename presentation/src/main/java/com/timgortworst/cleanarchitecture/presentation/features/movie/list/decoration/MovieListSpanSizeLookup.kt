package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager

class MovieListSpanSizeLookup(
    private val adapter: ConcatAdapter
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        val type = adapter.getItemViewType(position)

        adapter.adapters.forEachIndexed { index, adapter ->
            if (type == adapter.getItemViewType(index)) {
                return (adapter as? AdapterSpanSize)?.getSpanSize() ?: COLUMNS_SINGLE
            }
        }
        return COLUMNS_SINGLE
    }

    companion object {
        const val TOTAL_COLUMNS_GRID = 4
        const val FULL_WIDTH = TOTAL_COLUMNS_GRID
        const val HALF_WIDTH = FULL_WIDTH / 2
        const val COLUMNS_SINGLE = 1
    }
}