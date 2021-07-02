package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.content.res.Resources
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

interface AdapterDecoration {

    // forced to only use RecyclerView.ItemDecoration for item margins
    fun getItemDecoration(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ): Rect?
}
