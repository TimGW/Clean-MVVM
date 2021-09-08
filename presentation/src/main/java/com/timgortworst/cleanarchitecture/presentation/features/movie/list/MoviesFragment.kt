package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMediaListBinding
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatusBar
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.MainActivity
import com.timgortworst.cleanarchitecture.presentation.features.base.MoveListItemDecoration
import com.timgortworst.cleanarchitecture.presentation.features.watchprovider.MovieWatchProvidersDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val viewModel by viewModels<MoviesViewModel>()
    private var _binding: FragmentMediaListBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter by lazy {
        MoviesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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
            .inflateTransition(R.transition.shared_element_transition)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        setupToolbar()
        setupMovieList()
        observeUI()

        binding.recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

        binding.swiperefresh.setOnRefreshListener {
            movieAdapter.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_media_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_watch_providers -> {
                val dialog = MovieWatchProvidersDialog.newInstance()
                dialog.onCloseListener = { movieAdapter.refresh() }
                dialog.show(childFragmentManager, MovieWatchProvidersDialog.TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.layoutToolbar.toolbar)
        NavigationUI.setupWithNavController(
            binding.layoutToolbar.collapsingToolbarLayout,
            binding.layoutToolbar.toolbar,
            findNavController(),
            AppBarConfiguration.Builder(R.id.page_movies, R.id.page_settings).build()
        )
    }

    private fun setupMovieList() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.media_columns))
            adapter = movieAdapter
            movieAdapter.clickListener = { movie, view, transitionName ->
                navigateToDetails(movie, view, transitionName)
            }

            addItemDecoration(MoveListItemDecoration(
                resources.getDimension(R.dimen.keyline_8).toInt()
            ))
        }
    }

    private fun observeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesPaged.collectLatest { movieAdapter.submitData(it) }
        }

        lifecycleScope.launch {
            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                val loadState = loadStates.refresh

                binding.swiperefresh.isRefreshing = loadState is LoadState.Loading

                if (loadState is LoadState.Error) showError(loadState.error.localizedMessage)
                if (loadState !is LoadState.Loading) {
                    binding.noResults.visibility =
                        if (movieAdapter.itemCount == 0) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun showError(message: String?) {
        val bottomNavView = (requireActivity() as? MainActivity)
            ?.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        view?.snackbar(
            message = message ?: getString(R.string.generic_error),
            anchorView = bottomNavView
        )
    }

    private fun navigateToDetails(movie: Movie, sharedView: View, transitionName: String) {
        val directions =
            MoviesFragmentDirections.showMovieDetails(
                movie.title,
                movie.id,
                Movie.HIGH_RES_PREFIX + movie.posterPath,
                transitionName,
            )

        val extras = FragmentNavigatorExtras(sharedView to transitionName)
        findNavController().navigate(directions, extras)
    }
}
