package com.timgortworst.cleanarchitecture.presentation.features.tv.details

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
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShowDetails
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMediaDetailsBinding
import com.timgortworst.cleanarchitecture.presentation.extension.animateFade
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatus
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.base.AppBarOffsetListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailsFragment : Fragment(), AppBarOffsetListener.OnScrollStateListener {
    private val viewModel by viewModels<TvShowDetailViewModel>()
    private val args: TvShowDetailsFragmentArgs by navArgs()
    private var _binding: FragmentMediaDetailsBinding? = null
    private val binding get() = _binding!!
    private var isCollapsedTitleVisible = false
    private val animTime by lazy {
        resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    }
    private val appBarScrollListener = AppBarOffsetListener().also {
        it.scrollStateListener = this@TvShowDetailsFragment
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
            startEnterTransitionAfterLoadingImage(args.tvShowImage, this)
        }

        observeUI()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUI() {
        viewModel.tvShowDetails.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.INVISIBLE
            when (it) {
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Error -> view?.snackbar(getString(R.string.generic_error)) // FIXME show correct error
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is com.timgortworst.cleanarchitecture.domain.model.state.Resource.Result.Success -> presentTvShowDetails(it.data!!) // FIXME
            }
        }
    }

    private fun presentTvShowDetails(tvShowDetails: TvShowDetails) {
        binding.mediaDetailsReleaseDate.text =
            getString(R.string.media_detail_release_date, tvShowDetails.firstAirDate)

        binding.mediaDetailsOverview.text = tvShowDetails.overview
        binding.watchProviders.text =
            getString(R.string.watch_provider_availability, tvShowDetails.watchProviders) // FIXME: format nice list
        binding.expandedTitle.text = args.pageTitle
        binding.collapsedTitle.text = args.pageTitle
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
                    binding.appbar.setExpanded(false)
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

    override fun onScrollStateChangedListener(scrollState: AppBarOffsetListener.ScrollState) {
        binding.expandedTitle.alpha = scrollState.scrolledPercentile

        when (scrollState) {
            is AppBarOffsetListener.ScrollState.Collapsed -> {
                requireActivity().setTranslucentStatus(false)

                if (!isCollapsedTitleVisible) {
                    binding.collapsedTitle.animateFade(animTime, View.VISIBLE)
                    isCollapsedTitleVisible = true
                }
            }
            is AppBarOffsetListener.ScrollState.Expanded -> { /** do nothing **/ }
            is AppBarOffsetListener.ScrollState.Scrolling -> {
                requireActivity().setTranslucentStatus(true)

                if (isCollapsedTitleVisible) {
                    binding.collapsedTitle.animateFade(animTime, View.INVISIBLE)
                    isCollapsedTitleVisible = false
                }
            }
        }
    }
}
