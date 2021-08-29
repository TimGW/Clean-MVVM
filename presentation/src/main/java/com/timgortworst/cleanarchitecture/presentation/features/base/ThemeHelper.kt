package com.timgortworst.cleanarchitecture.presentation.features.base

import com.timgortworst.cleanarchitecture.presentation.features.base.ThemeHelperImpl.Companion.SAVED_NIGHT_VALUE

interface ThemeHelper {
    fun getAppTheme(): Int
    fun getNightMode(darkModeSetting: Int = SAVED_NIGHT_VALUE): Int
}