package com.timgortworst.cleanarchitecture.data.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject


class SharedPrefManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStringValue(key: String) =
        sharedPreferences.getString(key, "") ?: ""

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

    fun <T> setList(key: String, list: List<T>?) {
        val gson = Gson()
        sharedPreferences.edit().putString(key, gson.toJson(list)).apply()
    }

    inline fun <reified T> getList(key: String, context: Context): ArrayList<T> {
        val json = PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
            ?: return ArrayList()
        val type: Type = object: TypeToken<List<T>>(){}.type
        return Gson().fromJson(json, type)
    }
}
