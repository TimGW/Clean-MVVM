package com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter

import android.content.res.Resources
import android.graphics.Rect
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
        clickAction: (Movie, View, String) -> Unit,
    ): List<RecyclerView.Adapter<*>> {
        val padding = resources.getDimension(R.dimen.default_padding).toInt()
        val itemDecoration = NestedListItemDecoration(padding)

        val movieListAdapter = MovieListAdapter().apply {
            clickListener = clickAction
        }

        return listOf(
            HeaderAdapter("Featured", MovieListSpanSizeLookup.FULL_WIDTH),
            MovieFeaturedAdapter(movies.last(), MovieListSpanSizeLookup.FULL_WIDTH).apply {
                clickListener = clickAction
            },

            HeaderAdapter("Grid", MovieListSpanSizeLookup.FULL_WIDTH),
            MovieListGridAdapter(MovieListSpanSizeLookup.COLUMNS_SINGLE).apply {
                submitList(listOf(movies, movies).flatten())
                clickListener = clickAction
            },

            HeaderAdapter("Featured", MovieListSpanSizeLookup.FULL_WIDTH),
            MovieFeaturedAdapter(movies.last(), MovieListSpanSizeLookup.FULL_WIDTH).apply {
                clickListener = clickAction
            },

            HeaderAdapter("List", MovieListSpanSizeLookup.FULL_WIDTH),
            NestedRecyclerAdapter(movies, movieListAdapter, itemDecoration),
        )
    }
}