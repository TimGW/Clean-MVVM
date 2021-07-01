package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
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
        val featured1 = resources.getInteger(R.integer.featured_1_3_item_count)
        val featured2 = resources.getInteger(R.integer.featured_2_3_item_count)
        val featured3 = resources.getInteger(R.integer.featured_3_3_item_count)

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
                submitList(movies.take(featured1))
                clickListener = clickAction
            },

            // break 1
            HeaderAdapter("Kijken"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter().apply {
                clickListener = clickAction
            }, itemDecoration),

            HeaderAdapter(),

            // uitgelichte items 2/3 met break 2
            MovieListAdapter(calculateSpanWidth(totalGridColumns)).apply {
                submitList(movies.takeLast(featured2))
                clickListener = clickAction
            },

            // break 2
            MovieFeaturedAdapter(movies[3], calculateRelativeSpanWidth(totalGridColumns, break2Columns)),

            // uitgelichte items 3/3
            MovieListAdapter(calculateSpanWidth(totalGridColumns)).apply {
                submitList(movies.take(featured3))
                clickListener = clickAction
            },

            // sliders
            HeaderAdapter("Uitgelegd"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter().apply {
                clickListener = clickAction
            }, itemDecoration),
            HeaderAdapter("Collecties"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter().apply {
                clickListener = clickAction
            }, itemDecoration),
            HeaderAdapter("Sport"),
            NestedRecyclerAdapter(movies, NestedMovieListAdapter().apply {
                clickListener = clickAction
            }, itemDecoration),

        )
    }
}