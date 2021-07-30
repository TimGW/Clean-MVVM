package com.timgortworst.cleanarchitecture.domain.usecase.tv

import androidx.paging.filter
import com.timgortworst.cleanarchitecture.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetTvShowsUseCaseImpl @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) : GetTvShowsUseCase {

    override fun execute(params: Unit) = tvShowRepository.getTvShows().map { pagingData ->
        pagingData.filter {
            val date = if (it.firstAirDate.isNotBlank()) SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).parse(it.firstAirDate) else null

            date?.before(Date()) == true
        }
    }
}