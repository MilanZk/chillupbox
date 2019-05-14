package com.company.mobile.android.appname.app.common.view

import android.os.SystemClock
import android.view.View
import android.view.View.OnClickListener
import timber.log.Timber

/**
 * Implementation of [OnClickListener] that ignores subsequent clicks that happen too quickly after the first one.
 * To use this class, implement [.onSingleClick] instead of [OnClickListener.onClick].
 * See: https://stackoverflow.com/a/20348213/5189200
 */
abstract class OnSingleClickListener : OnClickListener {

    private var mLastClickTime: Long = 0

    override fun onClick(v: View) {
        val lastClickTime = mLastClickTime
        // See: http://sangsoonam.github.io/2017/03/01/do-not-use-curenttimemillis-for-time-interval.html
        val now = SystemClock.elapsedRealtime()
        mLastClickTime = now
        if (now - lastClickTime < MIN_DELAY_MS) {
            // Too fast: ignore
            Timber.d("onClick clicked too quickly: ignored")
        } else {
            // Register the click
            onSingleClick(v)
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    abstract fun onSingleClick(v: View)

    companion object {

        private const val MIN_DELAY_MS: Long = 1000

        /**
         * Wraps an [OnClickListener] into an [OnSingleClickListener].<br></br>
         * The argument's [OnClickListener.onClick] method will be called when a single click is registered.
         *
         * @param onClickListener The listener to wrap.
         * @return the wrapped listener.
         */
        fun wrap(onClickListener: OnClickListener): OnClickListener {
            return object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    onClickListener.onClick(v)
                }
            }
        }
    }
}
