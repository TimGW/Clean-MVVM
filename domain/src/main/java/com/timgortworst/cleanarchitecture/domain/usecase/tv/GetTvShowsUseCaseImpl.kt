package com.timgortworst.cleanarchitecture.domain.usecase.tv

import androidx.paging.filter
import com.timgortworst.cleanarchitecture.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetTvShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) : GetTvShowsUseCase {

    override fun execute(params: Unit) = tvShowRepository.getTvShows().map { pagingData ->
        pagingData.filter { it.firstAirDate?.before(Date()) == true }
    }
}