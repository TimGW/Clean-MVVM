package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade_in)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move)
        val delay = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        postponeEnterTransition(delay, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(args.uri).into( binding.movieDetailsImage)
        binding.movieDetailsImage.transitionName = args.uri

        observeUI()

        viewModel.fetchMovieDetails(args.movieId)
    }

    private fun observeUI() {
        viewModel.movies.observe(viewLifecycleOwner) { presentMovieDetails(it) }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        viewModel.error.observe(viewLifecycleOwner) {
            presentError(R.string.generic_error)
        }
    }

    private fun presentMovieDetails(movieDetails: MovieDetails) {
        binding.movieDetailsTitle.text = movieDetails.title
        binding.movieDetailsReleaseDate.text =
            getString(R.string.movie_detail_release_date, movieDetails.releaseDate)
        binding.movieDetailsOverview.text = movieDetails.overview
    }

    private fun presentError(errorMessage: Int) {
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text =
            getString(R.string.no_internet_placeholder_text, getString(errorMessage))
    }
}
