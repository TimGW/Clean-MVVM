package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridMarginDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)
        val decoration = getDecoration(parent, position) ?: return

        outRect.left = decoration.left
        outRect.top = decoration.top
        outRect.right = decoration.right
        outRect.bottom = decoration.bottom
    }

    private fun getDecoration(parent: RecyclerView, adapterPosition: Int): Rect? {
        val adapter = parent.findViewHolderForLayoutPosition(adapterPosition)?.bindingAdapter
        val relativePosition = parent.findViewHolderForLayoutPosition(adapterPosition)
            ?.bindingAdapterPosition ?: adapterPosition

        return (adapter as? AdapterDecoration)?.getItemDecoration(
            parent.resources,
            adapterPosition,
            relativePosition
        )
    }
}