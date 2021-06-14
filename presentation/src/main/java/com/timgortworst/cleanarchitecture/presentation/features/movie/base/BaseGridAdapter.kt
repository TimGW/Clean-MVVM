package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterSpanSize
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup

abstract class BaseGridAdapter<T, VB : ViewBinding>(
    private vararg val items: T
) : RecyclerView.Adapter<BaseViewHolder<T>>(), AdapterSpanSize, AdapterDecoration {

    abstract val itemViewType: Int
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun bind(binding: VB, item: T, position: Int)
    abstract override fun getItemOffset(parent: RecyclerView, view: View): Rect

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolderImpl(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // combine the layout and spansize to provide a unique but re-usable integer for the concatadapter
    // recycling between different adapters with the same layout
    override fun getItemViewType(position: Int): Int = itemViewType * getSpanSize()

    override fun getSpanSize(): Int = MovieListSpanSizeLookup.COLUMNS_SINGLE

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position], position)
    }

    inner class BaseViewHolderImpl(private val binding: VB) : BaseViewHolder<T>(binding) {
        override fun bind(item: T, position: Int) {
            this@BaseGridAdapter.bind(binding, item, position)
        }
    }
}