package com.timgortworst.cleanarchitecture.presentation.extension

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import android.widget.Toolbar
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.timgortworst.cleanarchitecture.presentation.R


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

fun Activity.setTranslucentStatus(isTranslucent: Boolean) {
    val flag = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    val flags = window.attributes.flags
    val isDarkModeEnabled = resources?.configuration
        ?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    val toolbar = findViewById<MaterialToolbar>(R.id.toolbar) ?: return

    // guard to prevent multiple calls when flag is already set or cleared
    if (((flags and flag) != 0 && isTranslucent) ||
        ((flags and flag) == 0 && !isTranslucent)
    ) return

    if (isTranslucent) {
        window.setFlags(flag, flag)
        window.decorView.clearLightStatusBar()
        toolbar.setUpButtonColor(android.R.color.white, theme)
    } else {
        if (isDarkModeEnabled) {
            window.decorView.clearLightStatusBar()
        } else {
            window.decorView.setLightStatusBar()
            toolbar.setUpButtonColor(android.R.color.black, theme)
        }
        window.clearFlags(flag)
    }
}

fun View.animateFade(duration: Long, visibility: Int) {
    val animSet = AnimationSet(true).apply {
        interpolator = LinearInterpolator()
        fillAfter = true
        this.duration = duration
    }

    val alphaAnimation = if (visibility == View.VISIBLE) {
        AlphaAnimation(0f, 1f)
    } else {
        AlphaAnimation(1f, 0f)
    }

    animSet.addAnimation(alphaAnimation)
    startAnimation(animSet)
}

fun MaterialToolbar?.setUpButtonColor(@ColorRes color: Int, theme: Resources.Theme? = null) {
    if (this == null) return
    navigationIcon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            ResourcesCompat.getColor(resources, color, theme),
            BlendModeCompat.SRC_ATOP)
}

private fun View.setLightStatusBar() {
    var flags = systemUiVisibility
    flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    this.systemUiVisibility = flags
}

private fun View.clearLightStatusBar() {
    var flags = systemUiVisibility
    flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    this.systemUiVisibility = flags
}