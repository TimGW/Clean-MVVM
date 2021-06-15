package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterSpanSize
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup

abstract class BaseListAdapter<T, VB: ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, BaseViewHolder<T>>(diffCallback), AdapterSpanSize {

    abstract val itemViewType: Int
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun bind(binding: VB, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolderImpl(binding)
    }

    // combine the layout and spansize to provide a unique but re-usable integer for the concatadapter
    // recycling between different adapters with the same layout
    override fun getItemViewType(position: Int): Int = itemViewType * getSpanSize()

    override fun getSpanSize(): Int = MovieListSpanSizeLookup.COLUMNS_SINGLE

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class BaseViewHolderImpl(private val binding: VB) : BaseViewHolder<T>(binding) {
        override fun bind(item: T, position: Int) {
            this@BaseListAdapter.bind(binding, item, position)
        }
    }

}