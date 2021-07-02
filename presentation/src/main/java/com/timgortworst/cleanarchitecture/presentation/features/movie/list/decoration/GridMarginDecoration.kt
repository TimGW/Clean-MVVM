package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

class GridMarginDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)
        val adapter = (parent.adapter as? ConcatAdapter) ?: return
        val decoration = adapter.getDecoration(parent, position) ?: return

        outRect.left = decoration.left
        outRect.top = decoration.top
        outRect.right = decoration.right
        outRect.bottom = decoration.bottom
    }

    private fun ConcatAdapter.getDecoration(parent: RecyclerView, adapterPosition: Int): Rect? {
        val currentItemType = getItemViewType(adapterPosition)
        val adapter = adapters.find { it.getItemViewType(adapterPosition) == currentItemType }
        val relativePosition = parent.findViewHolderForAdapterPosition(adapterPosition)
            ?.bindingAdapterPosition ?: return null

        return (adapter as? AdapterDecoration)?.getItemDecoration(
            parent.resources,
            adapterPosition,
            relativePosition
        )

        // this causes recycling issues
//        val adapter = recyclerView.findViewHolderForAdapterPosition(position)?.bindingAdapter
//        return (adapter as? AdapterDecoration)?.getItemDecoration(recyclerView.resources, position)
    }
}