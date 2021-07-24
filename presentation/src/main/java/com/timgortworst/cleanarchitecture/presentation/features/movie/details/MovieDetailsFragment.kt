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
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieDetailsBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatus
import com.timgortworst.cleanarchitecture.presentation.extension.animateSlideFade
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieDetailsBinding
    private var isCollapsedTitleVisible = false
    private val animTime by lazy {
        resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    }
    private val appBarScrollListener = AppBarLayout.OnOffsetChangedListener { layout, offset ->
        binding.expandedTitle.alpha = (1 - abs(offset).toFloat() / layout.totalScrollRange.toFloat())

        when {
            // fully collapsed
            abs(offset) == layout.totalScrollRange -> {
                requireActivity().setTranslucentStatus(false)

                if(!isCollapsedTitleVisible) {
                    binding.collapsedTitle.animateSlideFade(animTime, View.VISIBLE)
                    isCollapsedTitleVisible = true
                }
            }

            // fully expanded
            offset == 0 -> { /** do nothing **/ }

            // scrolling
            else -> {
                requireActivity().setTranslucentStatus(true)

                if (isCollapsedTitleVisible) {
                    binding.collapsedTitle.animateSlideFade(animTime, View.INVISIBLE)
                    isCollapsedTitleVisible = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.movie_detail_enter)
        returnTransition = inflater.inflateTransition(android.R.transition.fade)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    exitTransition = null
                }
            })
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieDetailsImage.apply {
            transitionName = args.transitionName
            startEnterTransitionAfterLoadingImage(args.movieImage, this)
        }

        observeUI()
        viewModel.setMovieId(args.movieId)
        requireActivity().setTranslucentStatus(true)
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
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.INVISIBLE
            when(it) {
                is Resource.Error -> presentError(R.string.generic_error)
                Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> presentMovieDetails(it.data)
            }
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
        binding.expandedTitle.text = args.pageTitle
        binding.collapsedTitle.text = args.pageTitle
    }

    private fun presentError(errorMessage: Int) {
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text =
            getString(R.string.no_internet_placeholder_text, getString(errorMessage))
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

    private fun setupToolbar() {
        val navController = findNavController()
        binding.collapsingToolbarLayout.setupWithNavController(
            binding.toolbar,
            navController,
            AppBarConfiguration(navController.graph)
        )
    }
}
