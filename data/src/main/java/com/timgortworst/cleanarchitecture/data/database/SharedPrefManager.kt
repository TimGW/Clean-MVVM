package com.timgortworst.cleanarchitecture.data.database

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
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
}
