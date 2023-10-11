package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.BindingInflater

abstract class SpannedAdapter<T, VB : ViewBinding>(
    private vararg val items: T
) : RecyclerView.Adapter<SpannedAdapter<T, VB>.BaseViewHolder>(), AdapterSpans {

    abstract val itemViewType: Int
    abstract val bindingInflater: BindingInflater<VB>

    abstract fun bind(binding: VB, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount() = if (items.isEmpty()) 1 else items.size

    // combine the layout and spansize to provide a unique but re-usable integer for the concatadapter
    // recycling between different adapters with the same layout
    //
    // for example, otherwise a HALF_WIDTH Adapter that has the same ViewHolder as a
    // FULL_WIDTH adapter will recycle the items when that is not desirable
    override fun getItemViewType(position: Int): Int = itemViewType + spans.systemSpans

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(items[position], position)
    }

    inner class BaseViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: T, position: Int) {
            bind(binding, item, position)
        }
    }
}