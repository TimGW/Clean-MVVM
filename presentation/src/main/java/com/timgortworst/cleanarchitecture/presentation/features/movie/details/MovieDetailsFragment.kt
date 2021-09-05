package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Result
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMediaDetailsBinding
import com.timgortworst.cleanarchitecture.presentation.extension.*
import com.timgortworst.cleanarchitecture.presentation.features.base.AppBarOffsetListener
import com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter.*
import com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter.GridSpanSizeLookup.Companion.calculateSpanWidth
import com.timgortworst.cleanarchitecture.presentation.model.Margins
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), AppBarOffsetListener.OnScrollStateListener {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private var _binding: FragmentMediaDetailsBinding? = null
    private val binding get() = _binding!!
    private val appBarScrollListener = AppBarOffsetListener().also {
        it.scrollStateListener = this@MovieDetailsFragment
    }

    private val concatAdapter by lazy {
        // If your Adapters share the same view types, and can support sharing ViewHolders between
        // added adapters, provide an instance of Config where you set Config#isolateViewTypes
        // to false. A common usage pattern for this is to return the R.layout.<layout_name> from
        // the Adapter#getItemViewType(int) method.
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())
    }

    private val relatedMoviesAdapter by lazy {
        RelatedMoviesAdapter()
    }

    private val spacing by lazy {
        resources.getDimension(R.dimen.keyline_16).toInt()
    }

    private val defaultMargins by lazy {
        Margins(left = spacing, top = spacing, right = spacing)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(requireContext())

        enterTransition = inflater.inflateTransition(R.transition.media_detail_enter)
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
        _binding = FragmentMediaDetailsBinding.inflate(layoutInflater)
        setupToolbar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mediaDetailsImage.apply {
            transitionName = args.transitionName
            startEnterTransitionAfterLoadingImage(args.movieImage, this)
        }
        setupRecyclerView()
        observeUI()
    }

    override fun onResume() {
        super.onResume()
        binding.appbar.addOnOffsetChangedListener(appBarScrollListener)
    }

    override fun onPause() {
        super.onPause()
        binding.appbar.removeOnOffsetChangedListener(appBarScrollListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUI() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            binding.progress.visibility =
                if (result is Result.Loading) View.VISIBLE else View.INVISIBLE
            result.data?.let { showMovieDetails(it) } ?: showEmptyState()
            result.error?.message?.let { showError(getString(it)) }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, GridSpanSizeLookup.FULL_WIDTH).apply {
                spanSizeLookup = GridSpanSizeLookup(concatAdapter)
            }
            adapter = concatAdapter
            addItemDecoration(GridMarginDecoration())
        }
    }

    private fun showError(message: String?) {
        view?.snackbar(message ?: getString(R.string.connection_error))
    }

    private fun showEmptyState() {
        binding.noResults.visibility = View.VISIBLE
    }

    private fun showMovieDetails(movieDetails: MovieDetails) {
        binding.expandedTitle.text = args.pageTitle
        binding.collapsedTitle.text = args.pageTitle

        concatAdapter.adapters.forEach { concatAdapter.removeAdapter(it) }
        // todo adapter binders
        // todo decorator pattern

        binding.noResults.visibility = View.GONE

        concatAdapter.addAdapter(StatisticsAdapter(
            movieDetails.status,
            movieDetails.voteAverage,
            movieDetails.voteCount,
            movieDetails.popularity,
            defaultMargins,
        ))

        concatAdapter.addAdapter(GenresAdapter(
            movieDetails.genres,
            defaultMargins,
        ))

        concatAdapter.addAdapter(TextAdapter(
            movieDetails.overview,
            defaultMargins,
        ))

        val watchProviders = movieDetails.watchProviders.map {
            (it.value.flatRate.orEmpty() + it.value.buy.orEmpty() + it.value.rent.orEmpty())
        }.joinToString()

        if (watchProviders.isNotBlank()) {
            concatAdapter.addAdapter(TextAdapter(
                getString(R.string.available_watch_providers, watchProviders),
                defaultMargins,
            ))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieCredits.collectLatest { result ->
                binding.progress.visibility =
                    if (result is Result.Loading) View.VISIBLE else View.INVISIBLE
                result.error?.message?.let { showError(getString(it)) }
                if (result.data != null) {
                    concatAdapter.addAdapter(TextAdapter(
                        getString(R.string.cast_and_crew),
                        defaultMargins,
                        R.style.TextAppearance_MyTheme_Headline5
                    ))

                    concatAdapter.addAdapter(CastAdapter(calculateSpanWidth(2)).apply {
                        submitList(result.data?.cast?.take(15))
                    })
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.relatedMovies.collectLatest { result ->
                binding.progress.visibility =
                    if (result is Result.Loading) View.VISIBLE else View.INVISIBLE
                result.error?.message?.let { showError(getString(it)) }
                if (result.data?.isNullOrEmpty() == false) {
                    concatAdapter.addAdapter(TextAdapter(
                        getString(R.string.also_watch),
                        defaultMargins,
                        R.style.TextAppearance_MyTheme_Headline5
                    ))
                    concatAdapter.addAdapter(
                        NestedRecyclerAdapter(
                            result.data!!,
                            relatedMoviesAdapter,
                            Margins(top = spacing / 2),
                            RelatedMoviesItemDecoration(spacing / 2),
                        )
                    )
                }
            }
        }
    }

    private fun startEnterTransitionAfterLoadingImage(uri: String, imageView: ImageView) {
        Glide.with(this)
            .load(uri)
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    setExpandedToolbar(false)
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    setExpandedToolbar(true)
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }

    private fun setExpandedToolbar(expanded: Boolean) {
        binding.collapsedTitle.alpha = if(expanded) 0f else 1f
        binding.appbar.setExpanded(expanded, false)
        if (expanded) binding.toolbar.setUpButtonColor(Color.WHITE)
        requireActivity().setTranslucentStatusBar(expanded)
    }

    private fun setupToolbar() {
        val navController = findNavController()
        binding.collapsingToolbarLayout.setupWithNavController(
            binding.toolbar,
            navController,
            AppBarConfiguration(navController.graph)
        )
    }

    override fun onScrollStateChangedListener(scrollState: AppBarOffsetListener.ScrollState) {
        val scrolled = scrollState.scrolledPercentile // (0..1)

        // the higher up the scrim trigger is, the faster the fade animation must be
        val speedMultiplier = 1 / (1 - SCRIM_TRIGGER_THRESHOLD)
        val fadeInAlpha = (scrolled - SCRIM_TRIGGER_THRESHOLD) * speedMultiplier

        binding.collapsingToolbarLayout.setScrimsShown(scrolled >= SCRIM_TRIGGER_THRESHOLD)
        binding.expandedTitle.alpha = 1 - scrolled // fade out
        binding.collapsedTitle.alpha = fadeInAlpha

        // only animate up-arrow color in light mode from white to black in collapsed mode
        if (!isNightModeActive() && scrollState is AppBarOffsetListener.ScrollState.Expanded) {
            binding.toolbar.setUpButtonColor(Color.WHITE)
        } else if (!isNightModeActive() && scrolled >= SCRIM_TRIGGER_THRESHOLD) {
            binding.toolbar.setUpButtonColor(blendARGB(Color.WHITE, Color.BLACK, fadeInAlpha))
        }
        requireActivity().setTranslucentStatusBar(scrolled < SCRIM_TRIGGER_THRESHOLD)
    }

    companion object {
        private const val SCRIM_TRIGGER_THRESHOLD = 0.75f // 3 quarter ways up
    }
}
