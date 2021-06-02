package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.timgortworst.cleanarchitecture.data.BuildConfig.BASE_URL_IMAGES
import com.timgortworst.cleanarchitecture.data.BuildConfig.BASE_URL_IMAGES_HIGH_RES
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.presentation.R
import com.timgortworst.cleanarchitecture.presentation.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadImage(BASE_URL_IMAGES.plus(args.moviePoster))

        observeUI()

        viewModel.fetchMovieDetails(args.movieId)
    }

    private fun observeUI() {
        viewModel.movies.observe(viewLifecycleOwner) { presentMovieDetails(it) }
        viewModel.loading.observe(viewLifecycleOwner) {
            progress_bar?.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        viewModel.error.observe(viewLifecycleOwner) {
            presentError(R.string.generic_error)
        }
    }

    private fun presentMovieDetails(movieDetails: MovieDetails) {
        movie_details_title.text = movieDetails.title
        movie_details_release_date.text =
            getString(R.string.movie_detail_release_date, movieDetails.releaseDate)
        movie_details_overview.text = movieDetails.overview

        loadImage(BASE_URL_IMAGES_HIGH_RES.plus(movieDetails.posterPath))
    }

    private fun presentError(errorMessage: Int) {
        error_message.visibility = View.VISIBLE
        error_message.text =
            getString(R.string.no_internet_placeholder_text, getString(errorMessage))
    }

    private fun loadImage(url: String) = Picasso.get().load(url)
        .noPlaceholder()
        .noFade()
        .centerCrop()
        .fit()
        .into(movie_details_image)
}
