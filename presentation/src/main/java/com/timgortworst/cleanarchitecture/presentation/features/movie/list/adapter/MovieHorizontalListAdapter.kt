package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.MovieListWrapperBinding
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.HorizontalListMarginDecoration

class MovieHorizontalListAdapter(
    private val movies: MutableList<Movie> = mutableListOf()
) : RecyclerView.Adapter<MovieHorizontalListAdapter.ViewHolder>() {
    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()
    private val viewPool = RecyclerView.RecycledViewPool()

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun getSectionID(position: Int): String {
        return movies.getOrNull(position)?.id?.toString().orEmpty()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)

        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] = holder.rv.layoutManager?.onSaveInstanceState()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieListWrapperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if (movies.isEmpty()) 0 else 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies)
    }

    fun addMoviesSection(movieList: List<Movie>) {
        movies.clear()
        movies.addAll(movieList)
        notifyDataSetChanged() // FIXME
//        notifyItemRangeChanged(0, movieList.size, movieList)

    }

    inner class ViewHolder(
        binding: MovieListWrapperBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val rv = binding.recyclerView

        fun bind(movies: List<Movie>) {
            val lm = LinearLayoutManager(rv.context, HORIZONTAL, false)
            lm.initialPrefetchItemCount = 4

            rv.apply {
                setRecycledViewPool(viewPool)
                layoutManager = lm
                adapter = MovieListAdapter(movies.toMutableList())
            }

            restoreState()
        }

        private fun restoreState() {
            val key = getSectionID(layoutPosition)
            val state = scrollStates[key]
            if (state != null) {
                rv.layoutManager?.onRestoreInstanceState(state)
            } else {
                val padding = rv.resources.getDimension(R.dimen.default_padding).toInt()
                rv.addItemDecoration(HorizontalListMarginDecoration(padding))
                rv.layoutManager?.scrollToPosition(0)
            }
        }
    }
}