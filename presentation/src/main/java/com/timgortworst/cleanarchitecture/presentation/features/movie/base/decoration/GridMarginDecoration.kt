package com.timgortworst.cleanarchitecture.presentation.features.movie.base.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/** Let the child adapters decide which margins/decorations to apply */
class GridMarginDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val decoration = getDecoration(parent, parent.getChildAdapterPosition(view)) ?: return

        outRect.left = decoration.left
        outRect.top = decoration.top
        outRect.right = decoration.right
        outRect.bottom = decoration.bottom
    }

    private fun getDecoration(parent: RecyclerView, adapterPosition: Int): Rect? {
        val childAdapter = parent.findViewHolderForLayoutPosition(adapterPosition)?.bindingAdapter
        val relativePosition = parent.findViewHolderForLayoutPosition(adapterPosition)
            ?.bindingAdapterPosition ?: adapterPosition

        return (childAdapter as? AdapterDecoration)?.getItemRect(
            parent.resources,
            adapterPosition,
            relativePosition
        )
    }
}