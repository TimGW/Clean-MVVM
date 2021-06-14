package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.NestedListItemDecoration

object AdapterFactory {

    fun createAdapters(
        resources: Resources,
        movies: List<Movie>,
        clickAction: (Movie, View) -> Unit,
    ): List<RecyclerView.Adapter<*>> {
        val padding = resources.getDimension(R.dimen.default_padding).toInt()
        val itemDecoration = NestedListItemDecoration(padding)

        val movieListAdapter = MovieListAdapter().apply {
            clickListener = clickAction
        }

        val grid = listOf(movies, movies, movies).flatten()

        return listOf(
            HeaderAdapter("Featured", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieFeaturedAdapter(movies.last(), MovieListSpanSizeLookup.FULL_WIDTH, padding).apply {
                clickListener = clickAction
            },

            HeaderAdapter("List", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            NestedRecyclerAdapter(movies, movieListAdapter, itemDecoration),

            HeaderAdapter("Grid", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieListGridAdapter(MovieListSpanSizeLookup.COLUMNS_SINGLE, padding).apply {
                submitList(grid)
                clickListener = clickAction
            },

            HeaderAdapter("Featured", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieFeaturedAdapter(movies.last(), MovieListSpanSizeLookup.FULL_WIDTH, padding).apply {
                clickListener = clickAction
            },

            HeaderAdapter("List", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            NestedRecyclerAdapter(movies, movieListAdapter, itemDecoration),

            HeaderAdapter("List", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            NestedRecyclerAdapter(movies, movieListAdapter, itemDecoration),

            HeaderAdapter("Grid", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieListGridAdapter(MovieListSpanSizeLookup.COLUMNS_SINGLE, padding).apply {
                submitList(grid)
                clickListener = clickAction
            },
        )
    }
}