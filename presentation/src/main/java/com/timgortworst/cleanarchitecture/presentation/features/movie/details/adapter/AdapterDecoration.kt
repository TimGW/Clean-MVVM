package com.timgortworst.cleanarchitecture.presentation.features.movie.details.adapter

import android.content.res.Resources
import android.graphics.Rect

interface AdapterDecoration {

    fun getItemDecoration(
        resources: Resources,
        adapterPosition: Int,
        relativePosition: Int
    ): Rect?
}