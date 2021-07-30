package com.timgortworst.cleanarchitecture.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.timgortworst.cleanarchitecture.data.model.movie.MovieDetailsEntity

@ProvidedTypeConverter
class TypeConverterMovie(private val moshi: Moshi) {

    @TypeConverter
    fun fromMovieGenreJson(value: String): List<MovieDetailsEntity.Genre>? {
        val type = Types.newParameterizedType(
            List::class.java,
            MovieDetailsEntity.Genre::class.java)

        val adapter: JsonAdapter<List<MovieDetailsEntity.Genre>> = moshi.adapter(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun toMovieGenreJson(list: List<MovieDetailsEntity.Genre>): String {
        val type = Types.newParameterizedType(
            List::class.java,
            MovieDetailsEntity.Genre::class.java)
        val adapter: JsonAdapter<List<MovieDetailsEntity.Genre>> = moshi.adapter(type)
        return adapter.toJson(list)
    }
}