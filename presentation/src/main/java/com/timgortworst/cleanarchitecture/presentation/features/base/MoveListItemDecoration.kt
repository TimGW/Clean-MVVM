package com.timgortworst.cleanarchitecture.presentation.features.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL

/**
 * Decoration for adding margin between blocks when used with a grid layout.
 */
class MoveListItemDecoration(
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
        val column = adapterPosition % maxSpanCount

        outRect.left = spacing - column * spacing / maxSpanCount
        outRect.right = (column + 1) * spacing / maxSpanCount
        if (adapterPosition < maxSpanCount) outRect.top = spacing
        outRect.bottom = spacing
    }
}