package com.timgortworst.cleanarchitecture.presentation.features.movie.base.decoration

import android.content.res.Resources
import android.graphics.Rect

interface AdapterDecoration {

    fun getItemRect(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ): Rect?
}
