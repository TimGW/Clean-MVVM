package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.Resource
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val movieId = MutableLiveData<Int>()
    val movieDetails: LiveData<Resource<MovieDetails>> =
        Transformations.switchMap(movieId) { movieId ->
            liveData {
                when (val res =
                    getMovieDetailsUseCase.execute(GetMovieDetailsUseCaseImpl.Params(movieId))) {
                    Resource.Loading -> emit(Resource.Loading)
                    is Resource.Success -> {
                        emit(Resource.Success(res.data))
                    }
                    is Resource.Error -> {
                        emit(Resource.Error())
                    }
                }
            }
        }

    fun setMovieId(movieId: Int) {
        this.movieId.value = movieId
    }
}