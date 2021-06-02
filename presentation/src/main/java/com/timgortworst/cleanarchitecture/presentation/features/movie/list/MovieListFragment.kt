package com.timgortworst.cleanarchitecture.presentation.features.movie.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.extension.snackbar
import com.timgortworst.cleanarchitecture.presentation.features.movie.details.MovieDetailViewModel
import com.timgortworst.cleanarchitecture.presentation.model.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val listViewModel by viewModels<MovieListViewModel>()

    private lateinit var adapter: MovieListAdapter
    private var movieDetailsClickListener: MovieDetailsClickListener? = null

    companion object {
        fun newInstance(): MovieListFragment {
            val fragment = MovieListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsClickListener) {
            movieDetailsClickListener = context
        } else {
            throw ClassCastException(context.toString() + " must implement MovieDetailsClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as? AppCompatActivity ?: return
        activity.setSupportActionBar(toolbar)
        activity.title = getString(R.string.movie_list_toolbar_title)
        swiperefresh?.isEnabled = false

        setupMovieList()
        observeUI()

        listViewModel.fetchMovies()
    }

    override fun onDetach() {
        movieDetailsClickListener = null
        super.onDetach()
    }

    private fun observeUI() {
        listViewModel.movie.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (it.isEmpty()) {
                    no_results.visibility = View.VISIBLE
                } else {
                    no_results.visibility = View.GONE
                    adapter.addMoviesToList(it.toMutableList())
                }
            }
        })

        listViewModel.loading.observe(viewLifecycleOwner, Observer {
            swiperefresh?.isRefreshing = it
        })

        listViewModel.error.observe(viewLifecycleOwner, EventObserver {
            view?.snackbar(it)
        })
    }

    private fun setupMovieList() {
        adapter = MovieListAdapter(mutableListOf()) { movie, moviePoster, transitionName ->
            movieDetailsClickListener?.onMovieClicked(movie, moviePoster, transitionName)
        }

        val columns = resources.getInteger(R.integer.gallery_columns)
        val orientation = resources.getInteger(R.integer.gallery_orientation)

        movie_list?.layoutManager = GridLayoutManager(activity, columns, orientation, false)
        movie_list?.adapter = adapter
    }

    interface MovieDetailsClickListener {
        fun onMovieClicked(movie: Movie, moviePoster: ImageView, transitionName: String)
    }
}
