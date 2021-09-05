package com.timgortworst.cleanarchitecture.presentation.extension

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import java.util.*


fun View.snackbar(
    message: String = "",
    actionMessage: String = "",
    anchorView: View? = null,
    length: Int = Snackbar.LENGTH_LONG,
    action: (() -> Unit)? = null
): Snackbar {
    val snackbar = Snackbar.make(this, message, length)
    if (action != null) snackbar.setAction(actionMessage) { action.invoke() }
    if (anchorView != null) snackbar.anchorView = anchorView
    snackbar.show()
    return snackbar
}

private fun Activity.isNightModeActive() = resources?.configuration
    ?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

fun Fragment.isNightModeActive() = resources.configuration
    ?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

fun Activity.setTranslucentStatusBar(translucent: Boolean) {
    val flag = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    val flags = window.attributes.flags

    // guard to prevent multiple calls when flag is already set or cleared
    if (((flags and flag) != 0 && translucent) ||
        ((flags and flag) == 0 && !translucent)
    ) return

    if (translucent) {
        window.setFlags(flag, flag)
        window.decorView.setLightStatusBarIcons()
    } else {
        if (isNightModeActive()) {
            window.decorView.setLightStatusBarIcons()
        } else {
            window.decorView.setDarkStatusBarIcons()
        }
        window.clearFlags(flag)
    }
}

private fun View.setDarkStatusBarIcons() {
    var flags = systemUiVisibility
    flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    this.systemUiVisibility = flags
}

private fun View.setLightStatusBarIcons() {
    var flags = systemUiVisibility
    flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    this.systemUiVisibility = flags
}

fun MaterialToolbar?.setUpButtonColor(@ColorInt color: Int) {
    if (this == null) return
    navigationIcon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
        color,
        BlendModeCompat.SRC_ATOP)
}

@ColorInt
fun blendARGB(
    @ColorInt fromColor: Int, @ColorInt toColor: Int,
    @FloatRange(from = 0.0, to = 1.0) ratio: Float
): Int {
    val inverseRatio = 1 - ratio
    val a: Float = Color.alpha(fromColor) * inverseRatio + Color.alpha(toColor) * ratio
    val r: Float = Color.red(fromColor) * inverseRatio + Color.red(toColor) * ratio
    val g: Float = Color.green(fromColor) * inverseRatio + Color.green(toColor) * ratio
    val b: Float = Color.blue(fromColor) * inverseRatio + Color.blue(toColor) * ratio
    return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    if (layoutParams is MarginLayoutParams) {
        val p = layoutParams as MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        requestLayout()
    }
}

fun Resources.dpToPixels(dp: Int): Int {
    // Get the screen's density scale
    val scale: Float = displayMetrics.density
    // Convert the dps to pixels, based on density scale
    return (dp * scale + 0.5f).toInt()
}

private val suffixes: NavigableMap<Int, String> = object : TreeMap<Int, String>() {
    init {
        put(1_000, "K")
        put(1_000_000, "M")
    }
}

fun format(value: Int): String {
    if (value == Integer.MIN_VALUE) return format(Integer.MIN_VALUE + 1)
    if (value < 0) return "-" + format(-value)
    if (value < 1000) return value.toString()

    val e: Map.Entry<Int, String> = suffixes.floorEntry(value)
    val divideBy = e.key
    val suffix = e.value
    val truncated = value / (divideBy / 10) //the number part of the output times 10
    val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()

    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}