package com.timgortworst.cleanarchitecture.presentation.features.tv.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.timgortworst.cleanarchitecture.domain.model.tv.TvShow
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMediaListBinding
import com.timgortworst.cleanarchitecture.presentation.extension.addSingleScrollDirectionListener
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatus
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.base.GridMarginDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : Fragment() {
    private val viewModel by viewModels<TvShowsViewModel>()
    private var _binding: FragmentMediaListBinding? = null
    private val binding get() = _binding!!

    private val tvShowGridAdapter by lazy {
        TvShowsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(android.R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaListBinding.inflate(layoutInflater, container, false)
        sharedElementReturnTransition = TransitionInflater.from(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        binding.toolbar.title = getString(R.string.tv_shows_list_toolbar_title)
        setupList()
        observeUI()

        binding.recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        requireActivity().setTranslucentStatus(false)

        binding.swiperefresh.setOnRefreshListener {
            tvShowGridAdapter.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tvShowsPaged.collectLatest { tvShowGridAdapter.submitData(it) }
        }

        lifecycleScope.launch {
            tvShowGridAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.noResults.visibility = View.GONE

                when (loadStates.refresh) {
                    is LoadState.NotLoading -> binding.swiperefresh.isRefreshing = false
                    LoadState.Loading -> {
                    }
                    is LoadState.Error -> {
                        binding.swiperefresh.isRefreshing = false
                        view?.snackbar(getString(R.string.connection_error))
                        if (tvShowGridAdapter.itemCount == 0) {
                            binding.noResults.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupList() {
        binding.recyclerView.apply {
            layoutManager =
                GridLayoutManager(activity, resources.getInteger(R.integer.media_columns))
            adapter = tvShowGridAdapter
            tvShowGridAdapter.clickListener = { tvShow, view, transitionName ->
                navigateToDetails(tvShow, view, transitionName)
            }

            val padding = resources.getDimension(R.dimen.default_padding).toInt()
            addItemDecoration(GridMarginDecoration(padding))
            addSingleScrollDirectionListener()
        }
    }

    private fun navigateToDetails(tvShow: TvShow, sharedView: View, transitionName: String) {
        val directions =
            TvShowsFragmentDirections.showTvShowDetails(
                tvShow.name,
                tvShow.id,
                tvShow.highResImage,
                transitionName,
            )

        val extras = FragmentNavigatorExtras(sharedView to transitionName)
        findNavController().navigate(directions, extras)
    }
}
