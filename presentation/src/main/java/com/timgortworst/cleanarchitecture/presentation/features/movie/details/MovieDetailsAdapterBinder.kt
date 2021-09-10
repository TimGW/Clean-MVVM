package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import android.content.Context
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ConcatAdapter
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.features.base.AdapterItemBinder
import com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter.*
import com.timgortworst.cleanarchitecture.presentation.model.Margins
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MovieDetailsAdapterBinder @Inject constructor(
    @ActivityContext private val context: Context
) : AdapterItemBinder<ConcatAdapter, MovieDetails> {
    private val relatedMoviesAdapter by lazy {
        RelatedMoviesAdapter()
    }

    private val spacing by lazy {
        context.resources.getDimension(R.dimen.keyline_16).toInt()
    }

    private val defaultMargins by lazy {
        Margins(left = spacing, top = spacing, right = spacing)
    }

    override fun addToAdapter(adapter: ConcatAdapter, item: MovieDetails) {
        with(adapter) {
            addStatistics(item.status, item.voteAverage, item.voteCount, item.popularity)
            addGenres(item.genres)
            addOverView(item.overview)
            addWatchProviders(item.watchProviders)
            addCast(item.cast)
            addRelatedMovies(item.recommendations)
        }
    }

    private fun ConcatAdapter.addStatistics(
        status: String,
        voteAverage: Double,
        voteCount: Int,
        popularity: Double
    ) {
        addAdapter(StatisticsAdapter(status, voteAverage, voteCount, popularity, defaultMargins))
    }

    private fun ConcatAdapter.addGenres(item: List<MovieDetails.Genre>) {
        addAdapter(GenresAdapter(item, defaultMargins))
    }

    private fun ConcatAdapter.addOverView(item: String) {
        addAdapter(TextAdapter(item, defaultMargins))
    }

    private fun ConcatAdapter.addWatchProviders(item: Map<String, MovieDetails.Provider>) {
        val watchProviders = item.map {
            (it.value.flatRate.orEmpty() + it.value.buy.orEmpty() + it.value.rent.orEmpty())
        }.joinToString()

        if (watchProviders.isNotBlank()) {
            addAdapter(
                TextAdapter(
                    context.resources.getString(R.string.available_watch_providers, watchProviders),
                    defaultMargins,
                )
            )
        }
    }

    private fun ConcatAdapter.addCast(item: List<Credits.Cast>) {
        val maxItems = 8

        addAdapter(
            TextAdapter(
                context.resources.getString(R.string.cast),
                defaultMargins,
                R.style.TextAppearance_MyTheme_Headline5
            )
        )

        val castAdapter = CastAdapter(
            GridSpanSizeLookup.calculateSpanWidth(
                context.resources.getInteger(R.integer.cast_columns)
            )
        ).apply {
            submitList(item.take(maxItems))
        }

        addAdapter(castAdapter)

        if (item.size > maxItems) {
            addAdapter(ButtonAdapter(
                context.getString(R.string.show_more),
                Margins(top = spacing / 2)
            ).apply {
                onClickListener = {
                    castAdapter.submitList(item)
                    removeAdapter(this)
                }
            })
        }
    }

    private fun ConcatAdapter.addRelatedMovies(item: List<Movie>) {
        if (item.isEmpty()) return

        relatedMoviesAdapter.clickListener = { movie, imageView, transitionName ->
            navigateToDetails(movie, imageView, transitionName)
        }

        addAdapter(
            TextAdapter(
                context.resources.getString(R.string.also_watch),
                defaultMargins,
                R.style.TextAppearance_MyTheme_Headline5
            )
        )
        addAdapter(
            NestedRecyclerAdapter(
                item,
                relatedMoviesAdapter,
                Margins(top = spacing / 2),
                RelatedMoviesItemDecoration(spacing / 2),
            )
        )
    }

    private fun navigateToDetails(movie: Movie, sharedView: View, transitionName: String) {
        val directions = MovieDetailsFragmentDirections.showMovieDetails(
            movie.title,
            movie.id,
            Movie.HIGH_RES_PREFIX + movie.posterPath,
            transitionName,
        )

        val extras = FragmentNavigatorExtras(sharedView to transitionName)
        findNavController(sharedView).navigate(directions, extras)
    }
}





