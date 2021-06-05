package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.appbar.AppBarLayout
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieDetailsBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieDetailsBinding
    private val appBarScrollListener = AppBarLayout.OnOffsetChangedListener { _, offset ->
        if (offset < -resources.getDimension(R.dimen.scrim_visible_height_trigger)) {
            requireActivity().setTranslucentStatus(false)
        } else {
            requireActivity().setTranslucentStatus(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.movie_detail_enter)
        returnTransition = inflater.inflateTransition(android.R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        setSharedElementTransition()
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        binding.collapsingToolbarLayout.setupWithNavController(
            binding.toolbar,
            navController,
            AppBarConfiguration(navController.graph)
        )

        binding.movieDetailsImage.apply {
            transitionName = args.uri
            startEnterTransitionAfterLoadingImage(args.uri, this)
        }

        observeUI()

        viewModel.fetchMovieDetails(args.movieId)
    }

    override fun onResume() {
        super.onResume()

        binding.appbar.addOnOffsetChangedListener(appBarScrollListener)
    }

    override fun onPause() {
        super.onPause()

        binding.appbar.removeOnOffsetChangedListener(appBarScrollListener)
    }

    private fun observeUI() {
        viewModel.movies.observe(viewLifecycleOwner) { presentMovieDetails(it) }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        viewModel.error.observe(viewLifecycleOwner) {
            presentError(R.string.generic_error)
        }
    }

    private fun presentMovieDetails(movieDetails: MovieDetails) {
        binding.movieDetailsReleaseDate.text =
            getString(R.string.movie_detail_release_date, movieDetails.releaseDate)

        var res = ""
        repeat(50) {
            res = res.plus(movieDetails.overview)
        }

        binding.movieDetailsOverview.text = res
        binding.toolbar.title = movieDetails.title
    }

    private fun presentError(errorMessage: Int) {
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text =
            getString(R.string.no_internet_placeholder_text, getString(errorMessage))
    }

    private fun setSharedElementTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    exitTransition = null
                }
            })
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
