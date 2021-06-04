package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.TransitionInflater
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.timgortworst.cleanarchitecture.domain.model.state.State
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieListBinding
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.model.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val listViewModel by viewModels<MovieListViewModel>()
    private lateinit var adapter: MovieListAdapter
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var noResults: TextView
    private lateinit var swiperefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade_out)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        // viewbinding can't be use with the ContextThemeWrapper
        val contextThemeWrapper = ContextThemeWrapper(context, R.style.MyTheme_DayNight)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_movie_list, container, false)
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

        setupMovieList()
        observeUI()

        postponeEnterTransition()
        recyclerView.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun observeUI() {
        listViewModel.movies.observe(viewLifecycleOwner, { response ->
            response?.let {
                if (it is Error) {
                    noResults.visibility = View.VISIBLE
                } else if(it is State.Success) {
                    noResults.visibility = View.GONE
                    adapter.addMoviesToList(it.data.toMutableList())
                }
            }
        })

        listViewModel.loading.observe(viewLifecycleOwner, {
//            swiperefresh.isRefreshing = it
        })

        listViewModel.error.observe(viewLifecycleOwner, EventObserver {
            view?.snackbar(it)
        })
    }

    private fun setupMovieList() {
        val columns = resources.getInteger(R.integer.gallery_columns)
        val orientation = resources.getInteger(R.integer.gallery_orientation)
        val padding = resources.getDimension(R.dimen.default_padding).toInt()

        adapter = MovieListAdapter { movie, imageView ->
            val directions =
                MovieListFragmentDirections.showMovieDetails(movie.id, movie.highResImage)
            val extras = FragmentNavigatorExtras(imageView to movie.highResImage)
            findNavController().navigate(directions, extras)
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, columns, orientation, false)
            adapter = this@MovieListFragment.adapter
            addItemDecoration(GridMarginDecoration(columns, padding))
        }
    }

    private fun setupViews() {
        collapsingToolbarLayout = requireView().findViewById(R.id.collapsing_toolbar_layout)
        toolbar = requireView().findViewById(R.id.toolbar)
        collapsingToolbarLayout = requireView().findViewById(R.id.collapsing_toolbar_layout)
        toolbar = requireView().findViewById(R.id.toolbar)
        recyclerView = requireView().findViewById(R.id.recyclerView)
        noResults = requireView().findViewById(R.id.no_results)
    }
}
