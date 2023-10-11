package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.extension.removeFirst
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.Spans
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.nested.NestedListItemDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.nested.NestedMovieListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.nested.NestedRecyclerAdapter

typealias BindingInflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

object AdapterFactory {

    fun createAdapters(
        resources: Resources,
        movies: List<Movie>,
        clickAction: (Movie, View) -> Unit,
    ): List<RecyclerView.Adapter<*>> {
        val padding = resources.getDimension(R.dimen.default_padding).toInt()
        val availableSpans = resources.getInteger(R.integer.grid_columns)
        val break2Spans = resources.getInteger(R.integer.break_2_columns)
        val nestedItemDecoration = NestedListItemDecoration(padding)
        val singleSpan = Spans.Absolute(availableSpans)
        val remainingMovies = movies.toMutableList()

        return listOf(
            // top item
            MovieFeaturedAdapter(movie = remainingMovies.removeFirst(), spans = Spans.FullWidth),

            // duo sub items
            HeaderAdapter(),
            MovieFeaturedAdapter(movie = remainingMovies.removeFirst(), spans = Spans.HalfWidth),
            MovieFeaturedAdapter(movie = remainingMovies.removeFirst(), spans = Spans.HalfWidth),

            // block items 1/3
            HeaderAdapter(),
            MovieListAdapter(spans = singleSpan).apply {
                val rows = resources.getInteger(R.integer.featured_item_rows)
                submitList(remainingMovies.removeFirst(rows * availableSpans))
            },

            // break 1
            HeaderAdapter(text = "Kijken"),
            NestedRecyclerAdapter(items = movies, itemAdapter = NestedMovieListAdapter(), itemDecoration = nestedItemDecoration),

            // block items 2/3
            HeaderAdapter(),
            MovieListAdapter(spans = singleSpan).apply {
                val rows = resources.getInteger(R.integer.featured_item_rows_before_break_2)
                submitList(remainingMovies.removeFirst(rows * availableSpans))
            },

            // break 2 (this item spans x number of columns)
            MovieFeaturedAdapter(
                movie = remainingMovies.removeFirst(),
                spans = Spans.Relative(visibleSpans = availableSpans, subSpans = break2Spans),
            ),
            // remainder of items after break 2
            MovieListAdapter(
                spans = Spans.Relative(visibleSpans = availableSpans, subSpans = 1),
                decoration = object : AdapterDecoration {
                    override fun getItemRect(
                        resources: Resources,
                        adapterPosition: Int,
                        relativePosition: Int
                    ): Rect {
                        val spacing = resources.getDimension(R.dimen.default_padding).toInt()
                        return Rect().apply { right = spacing }
                    }
                }
            ).apply {
                val break2RemainingItems = availableSpans - break2Spans
                submitList(remainingMovies.removeFirst(break2RemainingItems))
            },

            // block items 3/3
            MovieListAdapter(spans = singleSpan).apply {
                val rows = resources.getInteger(R.integer.featured_item_rows_after_break_2)
                submitList(remainingMovies.removeFirst(rows * availableSpans))
            },

            // horizontal sliders
            HeaderAdapter(text = "Explained"),
            NestedRecyclerAdapter(items = movies, itemAdapter = NestedMovieListAdapter(), itemDecoration = nestedItemDecoration),

            HeaderAdapter(text = "Collections"),
            NestedRecyclerAdapter(items = movies, itemAdapter = NestedMovieListAdapter(), itemDecoration = nestedItemDecoration),

            HeaderAdapter(text = "Sport"),
            NestedRecyclerAdapter(items = movies, itemAdapter = NestedMovieListAdapter(), itemDecoration = nestedItemDecoration),

            // remaining items
            HeaderAdapter(),
            MovieListAdapter(spans = singleSpan).apply {
                submitList(remainingMovies)
            },
        )
    }
}

fun ConcatAdapter.clear() {
    adapters.forEach { removeAdapter(it) }
}