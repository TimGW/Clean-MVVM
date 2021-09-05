package com.timgortworst.cleanarchitecture.data.remote.jsonAdapter

import com.squareup.moshi.FromJson
import com.timgortworst.cleanarchitecture.data.model.movie.MovieCreditsJson
import com.timgortworst.cleanarchitecture.domain.model.movie.Credits

class MovieCreditsJsonAdapter {

    @FromJson
    fun fromJson(json: MovieCreditsJson?): Credits? {
        if (json?.id == null) return null

        return Credits(
            json.id,
            json.cast.map {
                Credits.Cast(
                    it.adult,
                    it.gender,
                    it.id,
                    it.knownForDepartment,
                    it.name,
                    it.originalName,
                    it.popularity,
                    it.profilePath,
                    it.castId,
                    it.character,
                    it.creditId,
                    it.order,
                )
            },
            json.crew.map {
                Credits.Crew(
                    it.adult,
                    it.gender,
                    it.id,
                    it.knownForDepartment,
                    it.name,
                    it.originalName,
                    it.popularity,
                    it.profilePath,
                    it.creditId,
                    it.department,
                    it.job,
                )
            },
        )
    }
}