package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.base

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterSpanSize
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup

abstract class BaseGridAdapter<T>(
    private vararg val items: T
) : RecyclerView.Adapter<BaseViewHolder<T>>(), AdapterSpanSize, AdapterDecoration {

    abstract fun provideLayout(): Int

    abstract override fun getItemOffset(parent: RecyclerView, view: View): Rect

    abstract fun bind(itemView: View, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(provideLayout(), parent, false)
        return BaseViewHolderImpl(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // combine the layout and spansize to provide a unique but re-usable integer for the concatadapter
    // recycling between different adapters with the same layout
    override fun getItemViewType(position: Int): Int = provideLayout() * getSpanSize()

    override fun getSpanSize(): Int = MovieListSpanSizeLookup.COLUMNS_SINGLE

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position], position)
    }

    inner class BaseViewHolderImpl(itemView: View) : BaseViewHolder<T>(itemView) {
        override fun bind(item: T, position: Int) {
            this@BaseGridAdapter.bind(itemView, item, position)
        }
    }
}