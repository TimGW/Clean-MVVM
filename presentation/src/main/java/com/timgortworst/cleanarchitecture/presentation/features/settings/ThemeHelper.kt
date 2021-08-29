package com.timgortworst.cleanarchitecture.presentation.features.settings

import com.timgortworst.cleanarchitecture.presentation.features.settings.ThemeHelperImpl.Companion.SAVED_NIGHT_VALUE

interface ThemeHelper {
    fun getAppTheme(): Int
    fun getNightMode(darkModeSetting: Int = SAVED_NIGHT_VALUE): Int
}