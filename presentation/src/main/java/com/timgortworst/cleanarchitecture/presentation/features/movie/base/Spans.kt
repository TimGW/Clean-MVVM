package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import androidx.annotation.VisibleForTesting
import kotlin.math.ceil

// total grid of 60 to accommodate 1,2,3,4,5,6,10,12,15,20,30 evenly spread columns
private const val FULL_WIDTH = 60
private const val HALF_WIDTH = FULL_WIDTH / 2
private const val QUARTER_WIDTH = HALF_WIDTH / 2

sealed class Spans(val systemSpans: Int) {
    object FullWidth : Spans(FULL_WIDTH)
    object HalfWidth : Spans(HALF_WIDTH)
    object QuarterWidth : Spans(QUARTER_WIDTH)

    // Each column is 1/60 of the screen, so spanning a single column would appear very narrow.
    // This converts the actual required columns for the number of visible columns on screen.
    class Absolute(visibleSpans: Int) : Spans(FullWidth.systemSpans / visibleSpans)

    // For example you have a grid with 3 columns and you want to add an item that spans 60%
    class Relative(
        visibleSpans: Int,
        subSpans: Int,
    ) : Spans(calculateRelativeSpanWidth(visibleSpans, subSpans))

    companion object {

        @VisibleForTesting
        fun calculateRelativeSpanWidth(
            visibleSpans: Int,
            subSpans: Int
        ): Int {
            if (visibleSpans == 0 || subSpans == 0 || subSpans >= visibleSpans) return FullWidth.systemSpans
            val relativeSubSpans = (visibleSpans.toFloat() / subSpans.toFloat())
            val relativeTotalSpans = FullWidth.systemSpans.toFloat().div(relativeSubSpans)
            return ceil(relativeTotalSpans).toInt()
        }

        fun calculateMaxSpanCount(spans: Spans) = FullWidth.systemSpans / spans.systemSpans
    }
}
