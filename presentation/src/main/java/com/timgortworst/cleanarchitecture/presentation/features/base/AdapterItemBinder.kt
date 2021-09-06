package com.timgortworst.cleanarchitecture.presentation.features.base

import androidx.recyclerview.widget.RecyclerView

interface AdapterItemBinder<A : RecyclerView.Adapter<RecyclerView.ViewHolder>, T> {
    fun addToAdapter(adapter: A, item: T)
}