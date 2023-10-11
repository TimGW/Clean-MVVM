package com.timgortworst.cleanarchitecture.presentation.features.movie.base

import junit.framework.TestCase.assertEquals
import org.junit.Test

class SpansTest {
    private val totalColumns = 5
    private val break2Columns = 3
    private val break2Remaining = totalColumns - break2Columns

    @Test
    fun testCalculateSpanWidthRelative() {
        assertEquals(12, Spans.calculateRelativeSpanWidth(totalColumns, 1))
    }

    @Test
    fun testCalculateSpanWidthRelative2() {
        assertEquals(36, Spans.calculateRelativeSpanWidth(totalColumns, break2Columns))
    }
}