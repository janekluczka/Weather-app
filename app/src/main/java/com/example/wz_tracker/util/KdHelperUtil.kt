package com.example.wz_tracker.util

import java.util.*

object KdHelperUtil {
    fun calculateKd(kills: Int, deaths: Int): Float {
        return if (deaths == 0) kills.toFloat() / 1.0f else kills.toFloat() / deaths.toFloat()
    }

    fun formatKd(kd: Float) = String.format(Locale.ENGLISH, "%.2f", kd)
}