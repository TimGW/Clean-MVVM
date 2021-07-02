package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.AdapterDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.FULL_WIDTH
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.HALF_WIDTH
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.calculateRelativeSpanWidth
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.calculateSpanWidth
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.nested.NestedListItemDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.nested.NestedMovieListAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.nested.NestedRecyclerAdapter

object AdapterFactory {

    fun createAdapters(
        resources: Resources,
        movies: List<Movie>,
        clickAction: (Movie, View, String) -> Unit,
    ): List<RecyclerView.Adapter<*>> {
        val padding = resources.getDimension(R.dimen.default_padding).toInt()
        val itemDecoration = NestedListItemDecoration(padding)
        val totalGridColumns = resources.getInteger(R.integer.grid_columns)

        val break2Columns = resources.getInteger(R.integer.break_2_span)
        val break2FillerColumns = totalGridColumns - break2Columns
        val break2FillerItems = movies.take(break2FillerColumns)

        return listOf(
            // topstory
            MovieFeaturedAdapter(movies[0], FULL_WIDTH),

            HeaderAdapter(),

            // subtopstories
            MovieFeaturedAdapter(movies[1], HALF_WIDTH),
            MovieFeaturedAdapter(movies[2], HALF_WIDTH),

            HeaderAdapter(),

            // uitgelichte items 1/3
            MovieListAdapter(calculateSpanWidth(totalGridColumns)).apply {
//                submitList(listOf(movies, movies, movies, movies, movies, movies).flatten())
                submitList(movies.take(resources.getInteger(R.integer.featured_item_count)))
            },

            // break 1
            HeaderAdapter("Kijken"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter(), itemDecoration),

            HeaderAdapter(),

            // uitgelichte items boven break 2
            MovieListAdapter(calculateSpanWidth(totalGridColumns)).apply {
                submitList(movies.takeLast(totalGridColumns))
            },

            // break 2
            MovieFeaturedAdapter(
                movies[3],
                calculateRelativeSpanWidth(totalGridColumns, break2Columns)
            ),
            // break 2 filler items
            MovieListAdapter(calculateRelativeSpanWidth(totalGridColumns, break2FillerColumns) / break2FillerColumns,
                object : AdapterDecoration {
                    override fun getItemDecoration(
                        resources: Resources,
                        adapterPosition: Int,
                        relativePosition: Int
                    ): Rect {
                        val spacing = resources.getDimension(R.dimen.default_padding).toInt()
                        return Rect().apply { right = spacing}
                    }
                }
            ).apply {
                submitList(break2FillerItems)
            },

            // uitgelichte items 3/3
            MovieListAdapter(calculateSpanWidth(totalGridColumns)).apply {
                submitList(movies.take(totalGridColumns))
            },

            // sliders
            HeaderAdapter("Uitgelegd"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter(), itemDecoration),
            HeaderAdapter("Collecties"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter(), itemDecoration),
            HeaderAdapter("Sport"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter(), itemDecoration),
        )
    }
}