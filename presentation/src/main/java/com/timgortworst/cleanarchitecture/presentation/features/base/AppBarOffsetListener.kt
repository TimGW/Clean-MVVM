package com.timgortworst.cleanarchitecture.presentation.features.base

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class AppBarOffsetListener : AppBarLayout.OnOffsetChangedListener {
    var scrollStateListener: OnScrollStateListener? = null

    override fun onOffsetChanged(layout: AppBarLayout, offset: Int) {
        val scrolledPercentile = (1 - abs(offset).toFloat() / layout.totalScrollRange.toFloat())

        when {
            abs(offset) == layout.totalScrollRange -> scrollStateListener?.onScrollStateChangedListener(
                ScrollState.Collapsed(scrolledPercentile)
            )
            offset == 0 -> scrollStateListener?.onScrollStateChangedListener(
                ScrollState.Expanded(scrolledPercentile)
            )
            else -> scrollStateListener?.onScrollStateChangedListener(
                ScrollState.Scrolling(scrolledPercentile)
            )
        }
    }

    interface OnScrollStateListener {
        fun onScrollStateChangedListener(scrollState: ScrollState)
    }

    sealed class ScrollState {
        abstract val scrolledPercentile: Float

        data class Collapsed(override val scrolledPercentile: Float): ScrollState()
        data class Expanded(override val scrolledPercentile: Float): ScrollState()
        data class Scrolling(override val scrolledPercentile: Float): ScrollState()
    }
}