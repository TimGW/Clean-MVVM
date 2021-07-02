package com.timgortworst.cleanarchitecture.presentation.features.movie.list.decoration

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import kotlin.math.roundToInt

class GridSpanSizeLookup(
    private val adapter: ConcatAdapter
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        val currentItemType = adapter.getItemViewType(position)
        val adapter = adapter.adapters.find { it.getItemViewType(position) == currentItemType }
        return (adapter as? AdapterSpanSize)?.columnSpans ?: FULL_WIDTH
    }

    companion object {
        // total grid of 60 to accommodate 1,2,3,4,5,6,10,12,15,20,30 evenly spread columns
        const val FULL_WIDTH = 60
        const val HALF_WIDTH = FULL_WIDTH / 2

        fun calculateSpanWidth(numberOfColumns: Int): Int {
            return FULL_WIDTH / numberOfColumns
        }

        // For example you have a grid of 3 columns and you want to add an item
        // that spans 2 columns of the total of 3
        fun calculateRelativeSpanWidth(totalColumns: Int, totalSubColumns: Int): Int {
            if (totalColumns == 0 || totalSubColumns == 0) return FULL_WIDTH
            return (FULL_WIDTH.toFloat() / (totalColumns.toFloat() / totalSubColumns.toFloat())).roundToInt()
        }
    }
}