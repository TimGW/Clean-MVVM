package com.timgortworst.cleanarchitecture.presentation.features.movie.view

import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.squareup.picasso.Picasso
import com.timgortworst.cleanarchitecture.data.BuildConfig.BASE_URL_IMAGES
import com.timgortworst.cleanarchitecture.data.BuildConfig.BASE_URL_IMAGES_HIGH_RES
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.customview.DetailsTransition
import com.timgortworst.cleanarchitecture.presentation.features.movie.viewmodel.MovieDetailViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.ext.android.inject


class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by inject()

    private val movieId: Int by lazy {
        arguments?.getInt(MOVIE_ID_BUNDLE_KEY, INVALID_MOVIE_ID) ?: INVALID_MOVIE_ID
    }

    private val posterPath: String by lazy {
        arguments?.getString(MOVIE_POSTER_BUNDLE_KEY, "") ?: ""
    }

    companion object {
        private const val INVALID_MOVIE_ID = -1
        private const val MOVIE_ID_BUNDLE_KEY = "MOVIE_ID_BUNDLE_KEY"
        private const val MOVIE_POSTER_BUNDLE_KEY = "MOVIE_POSTER_BUNDLE_KEY"

        fun newInstance(movieId: Int, moviePoster: String? = null): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val args = Bundle()
            args.putInt(MOVIE_ID_BUNDLE_KEY, movieId)
            moviePoster?.let { args.putString(MOVIE_POSTER_BUNDLE_KEY, it) }
            fragment.arguments = args

            fragment.sharedElementEnterTransition = DetailsTransition()
            fragment.enterTransition = Fade()
            fragment.exitTransition = Fade()
            fragment.sharedElementReturnTransition = DetailsTransition()

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(BASE_URL_IMAGES.plus(posterPath))
            .centerCrop()
            .fit()
            .into(movie_details_image)

        viewModel.fetchMovieDetails(movieId)

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            presentMovieDetails(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progress_bar?.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            presentError(R.string.generic_error)
        })
    }

    private fun presentMovieDetails(movieDetails: MovieDetails) {
        movie_details_title.text = movieDetails.title
        movie_details_release_date.text =
            getString(R.string.movie_detail_release_date, movieDetails.releaseDate)
        movie_details_overview.text = movieDetails.overview

        // load high res image instead of low-res placeholder, short delay for animation to finish
        val delay = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        Handler().postDelayed({
            Picasso.get().load(BASE_URL_IMAGES_HIGH_RES.plus(movieDetails.posterPath))
                .noPlaceholder()
                .noFade()
                .centerCrop()
                .fit()
                .into(movie_details_image)
        }, delay)
    }

    private fun presentError(errorMessage: Int) {
        error_message.visibility = View.VISIBLE
        error_message.text =
            getString(R.string.no_internet_placeholder_text, getString(errorMessage))
    }
}
