package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieListBinding
import com.timgortworst.cleanarchitecture.presentation.extension.doNothing
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.GridSpanSizeLookup
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.Spans
import com.timgortworst.cleanarchitecture.presentation.features.movie.base.decoration.GridMarginDecoration
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.AdapterFactory
import com.timgortworst.cleanarchitecture.presentation.features.movie.list.adapter.clear
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel by viewModels<MovieListViewModel>()
    private lateinit var binding: FragmentMovieListBinding
    private val concatAdapter by lazy {
        // Setting setIsolateViewTypes to false, is an optimisation to share viewtypes between adapters
        // A common usage pattern for this is to return the R.layout.<layout_name> from the Adapter#getItemViewType(int) method.
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), Spans.FullWidth.systemSpans).apply {
                spanSizeLookup = GridSpanSizeLookup(concatAdapter)
            }
            adapter = concatAdapter
            addItemDecoration(GridMarginDecoration())
        }

        observeUI()
    }

    private fun observeUI() {
        viewModel.movies.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> addAdapters(it.data)
                else -> doNothing
            }
        }
    }

    private fun addAdapters(movies: List<Movie>) {
        concatAdapter.clear()

        AdapterFactory.createAdapters(resources, movies) { movie, _ ->
            // handle clicks
        }.forEach {
            concatAdapter.addAdapter(it)
        }
    }
}
