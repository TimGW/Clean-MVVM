package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager

/**
 * Returns the number of span occupied by the item at position. Let the adapter decide how many spans it should take. Defaults to use full width.
 */
class GridSpanSizeLookup(
    private val adapter: ConcatAdapter
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        val childAdapter = adapter.adapters.find { it.getItemViewType(position) == adapter.getItemViewType(position) }
        return (childAdapter as? AdapterSpans)?.spans?.systemSpans ?: Spans.FullWidth.systemSpans
    }
}