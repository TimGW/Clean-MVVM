package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import androidx.recyclerview.widget.GridLayoutManager

abstract class MaxSizeLookup: GridLayoutManager.SpanSizeLookup() {
    abstract fun getMaxSpanSize(): Int
}
