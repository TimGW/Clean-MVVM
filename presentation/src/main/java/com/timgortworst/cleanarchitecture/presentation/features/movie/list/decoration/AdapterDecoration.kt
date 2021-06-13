package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

interface AdapterDecoration {

    fun getItemOffset(
        parent: RecyclerView,
        view: View,
    ): Rect?
}
