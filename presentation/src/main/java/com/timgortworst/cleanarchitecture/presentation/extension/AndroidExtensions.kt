package com.timgortworst.cleanarchitecture.presentation.extension

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.view.animation.*
import androidx.recyclerview.widget.RecyclerView
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

fun Activity.setTranslucentStatus(isTranslucent: Boolean) {
    if (isTranslucent) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}

fun RecyclerView.addSingleScrollDirectionListener() {
    val listener = SingleScrollDirectionListener()
    addOnItemTouchListener(listener)
    addOnScrollListener(listener)
}

fun View.animateSlideFade(duration: Long, visibility: Int) {
    val animSet = AnimationSet(true).apply {
        interpolator = LinearInterpolator()
        fillAfter = true
        this.duration = duration
    }

    val translateAnim = if (visibility == View.VISIBLE){
        TranslateAnimation(0, 0f, 0, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 1f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f)
    } else {
        TranslateAnimation(0, 0f, 0, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 1f)
    }

    val alphaAnimation = if (visibility == View.VISIBLE){
        AlphaAnimation(0f, 1f)
    } else {
        AlphaAnimation(1f, 0f)
    }

    animSet.addAnimation(translateAnim)
    animSet.addAnimation(alphaAnimation)
    animSet.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {

        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    })

    startAnimation(animSet)
}