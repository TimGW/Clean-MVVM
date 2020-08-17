package com.timgortworst.cleanarchitecture.presentation.extension

import kotlinx.coroutines.Job

/**
 * Cancel the Job if it's active.
 */
fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancel()
    }
}