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

        return listOf(
            HeaderAdapter("Grid", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieListGridAdapter(MovieListSpanSizeLookup.COLUMNS_SINGLE, padding).apply {
                submitList(movies.take(11))
                clickListener = clickAction
            },
            HeaderAdapter("Featured", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieFeaturedAdapter(movies.first(), MovieListSpanSizeLookup.HALF_WIDTH, padding),
            MovieFeaturedAdapter(movies.last(), MovieListSpanSizeLookup.HALF_WIDTH, padding),
            HeaderAdapter("List", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            NestedRecyclerAdapter(movies, movieListAdapter, itemDecoration),
            HeaderAdapter("Featured", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieFeaturedAdapter(movies.last(), MovieListSpanSizeLookup.FULL_WIDTH, padding),
            HeaderAdapter("Grid", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            MovieListGridAdapter(MovieListSpanSizeLookup.COLUMNS_SINGLE, padding).apply {
                submitList(movies.take(6))
                clickListener = clickAction
            },
            HeaderAdapter("List", MovieListSpanSizeLookup.FULL_WIDTH, padding),
            NestedRecyclerAdapter(movies, movieListAdapter, itemDecoration),
        )
    }
}