package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var movieDetailsImage: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var movieDetailsTitle: TextView
    private lateinit var movieDetailsReleaseDate: TextView
    private lateinit var movieDetailsOverview: TextView
    private lateinit var error: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade_in)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()

        // viewbinding can't be use with the ContextThemeWrapper
        val contextThemeWrapper = ContextThemeWrapper(context, R.style.MyTheme_DayNight)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        val navController = findNavController()
        collapsingToolbarLayout.setupWithNavController(
            toolbar,
            navController,
            AppBarConfiguration(navController.graph)
        )

        movieDetailsImage.apply {
            transitionName = args.uri
            startEnterTransitionAfterLoadingImage(args.uri, this)
        }

        observeUI()

        viewModel.fetchMovieDetails(args.movieId)
    }

    private fun setupViews() {
        collapsingToolbarLayout = requireView().findViewById(R.id.collapsing_toolbar_layout)
        toolbar = requireView().findViewById(R.id.toolbar)
        movieDetailsImage = requireView().findViewById(R.id.movie_details_image)
        progressBar = requireView().findViewById(R.id.progress_bar)
        movieDetailsTitle = requireView().findViewById(R.id.movie_details_title)
        movieDetailsReleaseDate = requireView().findViewById(R.id.movie_details_release_date)
        movieDetailsOverview = requireView().findViewById(R.id.movie_details_overview)
        error = requireView().findViewById(R.id.error_message)
    }

    private fun observeUI() {
        viewModel.movies.observe(viewLifecycleOwner) { presentMovieDetails(it) }
        viewModel.loading.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        viewModel.error.observe(viewLifecycleOwner) {
            presentError(R.string.generic_error)
        }
    }

    private fun presentMovieDetails(movieDetails: MovieDetails) {
        movieDetailsTitle.text = movieDetails.title
        movieDetailsReleaseDate.text =
            getString(R.string.movie_detail_release_date, movieDetails.releaseDate)
        movieDetailsOverview.text = movieDetails.overview
        toolbar.title = movieDetails.title
    }

    private fun presentError(errorMessage: Int) {
        error.visibility = View.VISIBLE
        error.text =
            getString(R.string.no_internet_placeholder_text, getString(errorMessage))
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun startEnterTransitionAfterLoadingImage(uri: String, imageView: ImageView) {
        Glide.with(this)
            .load(uri)
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }
}
