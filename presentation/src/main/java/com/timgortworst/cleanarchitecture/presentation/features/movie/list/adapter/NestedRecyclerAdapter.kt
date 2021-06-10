package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListNestedBinding

class NestedRecyclerAdapter(
    private val movies: List<Movie>,
    private val movieListAdapter: MovieListAdapter,
    private val itemDecoration: RecyclerView.ItemDecoration,
) : RecyclerView.Adapter<NestedRecyclerAdapter.ViewHolder>() {
    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()
    private val viewPool = RecyclerView.RecycledViewPool()

    private fun getSectionID(position: Int): String {
        return movies[position].id.toString()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] = holder.recyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MovieListNestedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = if (movies.isEmpty()) 0 else 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies)
    }

    inner class ViewHolder(
        binding: MovieListNestedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val recyclerView = binding.recyclerView.apply {
            setRecycledViewPool(viewPool)
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(
                binding.recyclerView.context, HORIZONTAL, false
            ).apply { initialPrefetchItemCount = 4 }
        }

        fun bind(items: List<Movie>) {
            movieListAdapter.submitList(items)
            restoreState()
        }

        private fun restoreState() {
            val key = getSectionID(layoutPosition)
            val state = scrollStates[key]
            if (state != null) {
                recyclerView.layoutManager?.onRestoreInstanceState(state)
            } else {
                recyclerView.addItemDecoration(itemDecoration)
                recyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }
}