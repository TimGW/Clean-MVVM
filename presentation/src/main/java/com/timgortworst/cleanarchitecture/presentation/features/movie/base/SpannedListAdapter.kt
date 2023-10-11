package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class SpannedListAdapter<T, VB: ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, SpannedListAdapter<T,VB>.BaseViewHolder>(diffCallback), AdapterSpans {

    abstract val itemViewType: Int
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun bind(binding: VB, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    // combine the layout and spansize to provide a unique but re-usable integer for the concatadapter
    // recycling between different adapters with the same layout
    //
    // for example, otherwise a HALF_WIDTH Adapter that has the same ViewHolder as a
    // FULL_WIDTH adapter will recycle the items when that is not desirable
    override fun getItemViewType(position: Int): Int = itemViewType + spans.systemSpans

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    inner class BaseViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: T, position: Int) {
            bind(binding, item, position)
        }
    }
}