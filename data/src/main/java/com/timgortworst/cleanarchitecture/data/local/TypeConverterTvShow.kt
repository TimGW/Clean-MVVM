package com.timgortworst.cleanarchitecture.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.timgortworst.cleanarchitecture.data.model.tv.TvShowDetailsEntity

@ProvidedTypeConverter
class TypeConverterTvShow(private val moshi: Moshi) {

    @TypeConverter
    fun fromTvShowGenreJson(value: String): List<TvShowDetailsEntity.Genre>? {
        val type = Types.newParameterizedType(
            List::class.java,
            TvShowDetailsEntity.Genre::class.java)

        val adapter: JsonAdapter<List<TvShowDetailsEntity.Genre>> = moshi.adapter(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun toTvShowGenreJson(list: List<TvShowDetailsEntity.Genre>): String {
        val type = Types.newParameterizedType(
            List::class.java,
            TvShowDetailsEntity.Genre::class.java)
        val adapter: JsonAdapter<List<TvShowDetailsEntity.Genre>> = moshi.adapter(type)
        return adapter.toJson(list)
    }
}