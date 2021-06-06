package com.timgortworst.cleanarchitecture.presentation.features.movie.details

import androidx.lifecycle.*
import com.timgortworst.cleanarchitecture.domain.model.movie.MovieDetails
import com.timgortworst.cleanarchitecture.domain.model.state.State
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCase
import com.timgortworst.cleanarchitecture.domain.usecase.moviedetail.GetMovieDetailsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val movieId = MutableLiveData<Int>()
    val movieDetails: LiveData<State<MovieDetails>> =
        Transformations.switchMap(movieId) { movieId ->
            liveData {
                when (val res =
                    getMovieDetailsUseCase.execute(GetMovieDetailsUseCaseImpl.Params(movieId))) {
                    State.Loading -> emit(State.Loading)
                    is State.Success -> {
                        emit(State.Success(res.data))
                    }
                    is State.Error -> {
                        emit(State.Error())
                    }
                }
            }
        }

    fun setMovieId(movieId: Int) {
        this.movieId.value = movieId
    }
}