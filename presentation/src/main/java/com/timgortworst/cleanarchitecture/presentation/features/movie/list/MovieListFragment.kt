package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieListBinding
import com.timgortworst.cleanarchitecture.presentation.extension.addSingleScrollDirectionListener
import com.timgortworst.cleanarchitecture.presentation.extension.setTranslucentStatus
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.AdapterFactory
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridMarginDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.MovieListSpanSizeLookup.Companion.TOTAL_COLUMNS_GRID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel by viewModels<MovieListViewModel>()
    private lateinit var binding: FragmentMovieListBinding
    private val concatAdapter by lazy {
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())
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
//            binding.recyclerView.invalidateItemDecorations()
        }
        requireActivity().setTranslucentStatus(false)

        binding.swiperefresh.setOnRefreshListener {
            viewModel.reload()
        }
    }

    private fun observeUI() {
        viewModel.movies.observe(viewLifecycleOwner, {
            binding.noResults.visibility = View.GONE
            binding.swiperefresh.isRefreshing = false
            when (it) {
                is Resource.Error -> {
                    it.errorEntity?.message?.let { msg -> view?.snackbar(msg) }
                    binding.noResults.visibility = View.VISIBLE
                }
                Resource.Loading -> binding.swiperefresh.isRefreshing = true
                is Resource.Success -> setupAdapters(it.data)
            }
        })
    }

    private fun setupMovieList() {
        val spanLookup = MovieListSpanSizeLookup(concatAdapter)
        val padding = resources.getDimension(R.dimen.default_padding).toInt()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, TOTAL_COLUMNS_GRID).apply {
                spanSizeLookup = spanLookup
                adapter = concatAdapter
            }
            addItemDecoration(GridMarginDecoration(padding))
            addSingleScrollDirectionListener()
        }
    }

    private fun setupAdapters(movies: List<Movie>) {
        if (movies.isEmpty()) return

        concatAdapter.adapters.forEach { concatAdapter.removeAdapter(it) }

        val adapters = AdapterFactory.createAdapters(resources, movies) { movie, view, transitionName ->
            navigateToDetails(movie, view, transitionName)
        }

        adapters.forEach { concatAdapter.addAdapter(it) }
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
