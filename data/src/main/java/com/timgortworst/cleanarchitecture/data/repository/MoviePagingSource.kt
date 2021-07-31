package com.timgortworst.cleanarchitecture.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timgortworst.cleanarchitecture.data.local.SharedPrefs
import com.timgortworst.cleanarchitecture.data.remote.MovieService
import com.timgortworst.cleanarchitecture.domain.model.movie.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource (
    private val movieService: MovieService,
    private val sharedPrefs: SharedPrefs,
) : PagingSource<Int, Movie>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        return try {
            val region = sharedPrefs.getWatchProviderRegion()
            val watchProviders = sharedPrefs.getWatchProvidersMovie()

            val nextPageNumber = params.key ?: 1
            val response = movieService.getMovies(
                page = nextPageNumber,
                region = region,
                watchProviderIds = watchProviders?.map { it.providerId }?.joinToString(separator = "|"),
                watchProviderRegion = if(watchProviders != null) region else null,
                monetizationTypes = if(watchProviders != null) "flatrate|free|ads" else null
            ).body() ?: run { return LoadResult.Error(Throwable()) }

            val nextPage = if (response.page < response.totalPages) response.page + 1 else null

            LoadResult.Page(
                data = response.results,
                prevKey = null, // Only paging forward.
                nextKey = nextPage
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}