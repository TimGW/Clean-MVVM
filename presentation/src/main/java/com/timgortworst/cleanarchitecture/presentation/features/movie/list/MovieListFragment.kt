package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.os.Bundle
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
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration.GridSpanSizeLookup.Companion.FULL_WIDTH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel by viewModels<MovieListViewModel>()
    private lateinit var binding: FragmentMovieListBinding
    private val concatAdapter by lazy {
        // If your Adapters share the same view types, and can support sharing ViewHolders between
        // added adapters, provide an instance of Config where you set Config#isolateViewTypes
        // to false. A common usage pattern for this is to return the R.layout.<layout_name> from
        // the Adapter#getItemViewType(int) method.
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
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, FULL_WIDTH).apply {
                spanSizeLookup = GridSpanSizeLookup(concatAdapter)
            }
            adapter = concatAdapter
            addItemDecoration(GridMarginDecoration())
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
