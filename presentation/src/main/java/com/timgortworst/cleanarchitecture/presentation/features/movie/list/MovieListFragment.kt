package com.timgortworst.cleanarchitecture.presentation.features.movie.list

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
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieListBinding
import com.timgortworst.cleanarchitecture.presentation.extension.addSingleScrollDirectionListener
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatus
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.MovieListGridAdapter
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridMarginDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel by viewModels<MovieListViewModel>()
    private lateinit var binding: FragmentMovieListBinding
    private val movieAdapter by lazy {
        MovieListGridAdapter()
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
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        sharedElementReturnTransition = TransitionInflater.from(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        setupMovieList()
        observeUI()

        binding.recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        requireActivity().setTranslucentStatus(false)

        binding.swiperefresh.setOnRefreshListener {
            movieAdapter.refresh()
        }
    }

    private fun observeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesPaged.collectLatest { movieAdapter.submitData(it) }
        }

        lifecycleScope.launch {
            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.noResults.visibility = View.GONE

                when (loadStates.refresh) {
                    is LoadState.NotLoading -> binding.swiperefresh.isRefreshing = false
                    LoadState.Loading -> { }
                    is LoadState.Error -> {
                        binding.swiperefresh.isRefreshing = false
                        view?.snackbar(getString(R.string.connection_error))
                        if (movieAdapter.itemCount == 0) {
                            binding.noResults.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupMovieList() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.movie_columns))
            adapter = movieAdapter
            movieAdapter.clickListener = { movie, view, transitionName ->
                navigateToDetails(movie, view, transitionName)
            }

            val padding = resources.getDimension(R.dimen.default_padding).toInt()
            addItemDecoration(GridMarginDecoration(padding))
            addSingleScrollDirectionListener()
        }
    }

    private fun navigateToDetails(movie: Movie, sharedView: View, transitionName: String) {
        val directions =
            MovieListFragmentDirections.showMovieDetails(
                movie.title,
                movie.id,
                movie.highResImage,
                transitionName,
            )

        val extras = FragmentNavigatorExtras(sharedView to transitionName)
        findNavController().navigate(directions, extras)
    }
}
