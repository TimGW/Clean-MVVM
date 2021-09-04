package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RelatedMoviesItemDecoration(
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

        outRect.left = if (position == 0) spacing * 2 else spacing
        if (position == lastItemPosition) outRect.right = spacing * 2
    }
}