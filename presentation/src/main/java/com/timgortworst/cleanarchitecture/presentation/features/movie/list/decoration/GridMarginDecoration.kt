package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.timgortworst.cleanarchitecture.presentation.R

/**
 * Decoration for adding margin between blocks when used with a grid layout.
 */
class GridMarginDecoration(
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager as? GridLayoutManager
        if (layoutManager == null || layoutManager.orientation != VERTICAL) {
            return super.getItemOffsets(outRect, view, parent, state)
        }

        val adapterPosition: Int = parent.getChildAdapterPosition(view)
        val maxSpanCount = layoutManager.spanCount

        when (val viewType = parent.adapter?.getItemViewType(adapterPosition)) {
            R.layout.movie_list_item_featured,
            R.layout.movie_list_header -> {
                outRect.left = spacing
                outRect.right = spacing
            }
            R.layout.movie_list_item -> {
                outRect.addGridMargin(parent, adapterPosition, maxSpanCount, viewType)
            }
        }
    }

    private fun Rect.addGridMargin(
        parent: RecyclerView,
        adapterPosition: Int,
        maxSpanCount: Int,
        viewType: Int
    ) {
        val position = parent.getRelativeItemPosition(adapterPosition, viewType)
        val column = position % maxSpanCount

        left = spacing - column * spacing / maxSpanCount
        right = (column + 1) * spacing / maxSpanCount
        if (position < maxSpanCount) top = spacing
        bottom = spacing
    }

    private fun RecyclerView.getRelativeItemPosition(
        adapterPosition: Int,
        childViewType: Int
    ): Int {
        return generateSequence(adapterPosition - 1) { it.dec() }
            .takeWhile { adapter?.getItemViewType(it) == childViewType }
            .count()
    }
}