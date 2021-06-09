package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListNestedBinding

class NestedRecyclerAdapter<T : RecyclerView.Adapter<*>>(
    private val listItemAdapter: T,
    private val itemDecoration: RecyclerView.ItemDecoration
) : RecyclerView.Adapter<NestedRecyclerAdapter<T>.ViewHolder>() {
    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()
    private val viewPool = RecyclerView.RecycledViewPool()

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun getSectionID(position: Int): String {
        return listItemAdapter.getItemId(position).toString()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] = holder.rv.layoutManager?.onSaveInstanceState()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListNestedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = if (listItemAdapter.itemCount > 0) 1 else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItemAdapter)
    }

    inner class ViewHolder(
        binding: MovieListNestedBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val rv = binding.recyclerView
        private val lm = LinearLayoutManager(rv.context, HORIZONTAL, false).apply {
            initialPrefetchItemCount = 4
        }

        fun bind(itemAdapter: T) {
            rv.apply {
                setRecycledViewPool(viewPool)
                layoutManager = lm
                adapter = itemAdapter
            }

            restoreState()
        }

        private fun restoreState() {
            val key = getSectionID(layoutPosition)
            val state = scrollStates[key]
            if (state != null) {
                rv.layoutManager?.onRestoreInstanceState(state)
            } else {
                rv.addItemDecoration(itemDecoration)
                rv.layoutManager?.scrollToPosition(0)
            }
        }
    }
}