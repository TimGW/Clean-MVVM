package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalListMarginDecoration(
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)
        val lastItemPosition = parent.adapter?.itemCount?.minus(1)

        outRect.left = spacing
        if (position == lastItemPosition) outRect.right = spacing
        outRect.top = spacing
        outRect.bottom = spacing
    }
}