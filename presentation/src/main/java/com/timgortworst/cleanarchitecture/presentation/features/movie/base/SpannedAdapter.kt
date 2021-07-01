package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterSpanSize

abstract class SpannedAdapter<T, VB : ViewBinding>(
    private vararg val items: T
) : RecyclerView.Adapter<BaseViewHolder<T>>(), AdapterSpanSize {

    abstract val itemViewType: Int
    abstract override val columnSpans: Int
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun bind(binding: VB, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolderImpl(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // combine the layout and spansize to provide a unique but re-usable integer for the concatadapter
    // recycling between different adapters with the same layout
    //
    // for example, otherwise a HALF_WIDTH Adapter that has the same ViewHolder as a
    // FULL_WIDTH adapter will recycle the items when that is not desirable
    override fun getItemViewType(position: Int): Int = itemViewType * columnSpans

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position], position)
    }

    inner class BaseViewHolderImpl(private val binding: VB) : BaseViewHolder<T>(binding) {
        override fun bind(item: T, position: Int) {
            this@SpannedAdapter.bind(binding, item, position)
        }
    }
}