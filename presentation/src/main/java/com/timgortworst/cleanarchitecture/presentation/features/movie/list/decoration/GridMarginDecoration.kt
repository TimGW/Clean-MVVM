package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.VERTICAL

/**
 * Item decorations are delegated to the adapters that implement the AdapterDecoration interface
 */
class GridMarginDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager as? GridLayoutManager
        val adapterPosition: Int = parent.getChildAdapterPosition(view)

        if (layoutManager == null ||
            layoutManager.orientation != VERTICAL ||
            adapterPosition == NO_POSITION
        ) {
            return super.getItemOffsets(outRect, view, parent, state)
        }

        val vh = parent.findViewHolderForAdapterPosition(adapterPosition)
        val decoration = (vh?.bindingAdapter as? AdapterDecoration)?.getItemOffset(parent, view)

        outRect.bottom = decoration?.bottom ?: 0
        outRect.left = decoration?.left ?: 0
        outRect.top = decoration?.top ?: 0
        outRect.right = decoration?.right ?: 0
    }
}