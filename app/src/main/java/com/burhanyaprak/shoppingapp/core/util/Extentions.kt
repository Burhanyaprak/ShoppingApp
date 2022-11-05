package com.burhanyaprak.shoppingapp.core.util

import android.animation.ObjectAnimator
import android.widget.TextView

fun TextView.cycleTextViewExpansion(tv: TextView, collapsedMaxLines: Int = 2) {
    val animation = ObjectAnimator.ofInt(
        tv, "maxLines",
        if (tv.maxLines == collapsedMaxLines) tv.lineCount else collapsedMaxLines
    )
    animation.setDuration(150).start()
}