package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieListBinding
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.model.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val listViewModel by viewModels<MovieListViewModel>()
    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMovieList()
        observeUI()

        listViewModel.fetchMovies()
    }

    private fun observeUI() {
        listViewModel.movie.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (it.isEmpty()) {
                    binding.noResults.visibility = View.VISIBLE
                } else {
                    binding.noResults.visibility = View.GONE
                    movieAdapter.addMoviesToList(it.toMutableList())
                }
            }
        })

        listViewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.swiperefresh.isRefreshing = it
        })

        listViewModel.error.observe(viewLifecycleOwner, EventObserver {
            view?.snackbar(it)
        })
    }

    private fun setupMovieList() {
        val columns = resources.getInteger(R.integer.gallery_columns)
        val orientation = resources.getInteger(R.integer.gallery_orientation)

        binding.movieList.addItemDecoration(InnerGridMarginRvItemDecoration(columns,
            resources.getDimension(R.dimen.default_padding).toInt()))

        movieAdapter = MovieListAdapter(mutableListOf()) { view, movie ->
            view.findNavController().navigate(
                MovieListFragmentDirections.showMovieDetails(
                    movie.id,
                    movie.posterPath.orEmpty()
                )
            )
        }


        binding.movieList.apply {
            this.adapter = movieAdapter
            this.layoutManager = GridLayoutManager(activity, columns, orientation, false)

//            postponeEnterTransition()
//            viewTreeObserver.addOnPreDrawListener {
//                startPostponedEnterTransition()
//                true
//            }
        }
    }
}
