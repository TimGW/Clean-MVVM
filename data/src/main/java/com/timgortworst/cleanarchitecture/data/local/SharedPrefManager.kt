package com.timgortworst.cleanarchitecture.data.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPrefManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStringValue(key: String, default: String? = null) =
        sharedPreferences.getString(key, default) ?: default

    fun setStringValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getLongValue(key: String, default: Long = 0L) =
        sharedPreferences.getLong(key, default)

    fun setLongValue(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getIntValue(key: String, default: Int = 0) =
        sharedPreferences.getInt(key, default)

    fun setIntValue(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getBoolValue(key: String, default: Boolean = false) =
        sharedPreferences.getBoolean(key, default)

    fun setBoolValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    inline fun <reified T> setList(
        context: Context,
        moshi: Moshi,
        key: String,
        list: List<T>?
    ) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(type)

        sharedPreferences.edit().putString(key, adapter.toJson(list)).apply()
    }

    inline fun <reified T> getList(
        context: Context,
        moshi: Moshi,
        key: String
    ): List<T>? {
        val json = PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
            ?: return null
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(type)

        return adapter.fromJson(json)
    }
}
