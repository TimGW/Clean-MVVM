package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterSpanSize
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup.Companion.COLUMNS_SINGLE

abstract class BaseGridAdapter<VH : RecyclerView.ViewHolder>(
) : RecyclerView.Adapter<VH>(), AdapterSpanSize, AdapterDecoration {

    abstract fun provideItemViewType(): Int

    abstract override fun getItemOffset(parent: RecyclerView, view: View): Rect

    override fun getItemViewType(position: Int): Int = provideItemViewType() * getSpanSize()

    override fun getSpanSize(): Int = COLUMNS_SINGLE
}