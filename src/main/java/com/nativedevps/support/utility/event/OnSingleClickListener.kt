package com.nativedevps.support.utility.event

import android.os.SystemClock
import android.view.View

/**
 * https://stackoverflow.com/a/20672997/10110990
 */
abstract class SingleClickListener constructor(private val minClickInterval: Int = 1000) :
    View.OnClickListener {
    private var mLastClickTime: Long = 0

    /**
     * click
     * @param v The view that was clicked.
     */
    abstract fun onSingleClick(v: View?)
    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= minClickInterval) return
        onSingleClick(v)
    }

    companion object {
    }
}