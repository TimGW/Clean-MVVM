package com.timgortworst.cleanarchitecture.presentation.extension

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar


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

fun Activity.isDarkModeEnabled() = resources?.configuration
    ?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

fun Fragment.isDarkModeEnabled() = resources.configuration
    ?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

fun Activity.setTranslucentStatus(translucent: Boolean) {
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
        if (isDarkModeEnabled()) {
            window.decorView.setLightStatusBarIcons()
        } else {
            window.decorView.setDarkStatusBarIcons()
        }
        window.clearFlags(flag)
    }
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