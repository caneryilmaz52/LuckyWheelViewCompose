package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.annotation.FloatRange
import java.io.Serializable

/**
 * @param iconSizeMultiplier is item icon size multiplier value, default icon size `100px`
 * @param iconPositionFraction
 * * is icon vertical position fraction in wheel slice
 * - The smaller the value, the closer to the center
 * - The larger the value, the closer to the corners
 */
data class ItemIconStyle(
    val iconSizeMultiplier: Float = 1.0F,
    @FloatRange(from = 0.1, to = 0.9) val iconPositionFraction: Float = 0.5F
) : Serializable
