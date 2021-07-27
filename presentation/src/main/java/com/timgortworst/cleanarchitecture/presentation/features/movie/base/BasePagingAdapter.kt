package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class BasePagingAdapter<T : Any, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : PagingDataAdapter<T, BaseViewHolder<T>>(diffCallback) {

    abstract val itemViewType: Int
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun bind(binding: VB, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolderImpl(binding)
    }

    override fun getItemViewType(position: Int): Int = itemViewType

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    inner class BaseViewHolderImpl(private val binding: VB) : BaseViewHolder<T>(binding) {
        override fun bind(item: T, position: Int) {
            this@BasePagingAdapter.bind(binding, item, position)
        }
    }
}