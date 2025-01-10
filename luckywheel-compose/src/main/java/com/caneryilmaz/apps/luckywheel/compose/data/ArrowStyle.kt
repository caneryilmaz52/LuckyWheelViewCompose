package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.caneryilmaz.apps.luckywheel.compose.R
import com.caneryilmaz.apps.luckywheel.compose.constant.ArrowPosition
import java.io.Serializable

/**
 * @param arrowId is wheel arrow drawable resource id
 * @param arrowSizeDp is size of wheel arrow image
 * @param arrowColor is wheel arrow tint color
 * @param arrowPosition is wheel arrow position [ArrowPosition.TOP] or [ArrowPosition.CENTER]
 * @param arrowOffsetY
 * * is wheel arrow offset on Y axis
 * - if value is positive then arrow moving down
 * - if value is negative then arrow moving up
 * @param arrowAnimationStatus is enable or disable arrow swing animation
 * @param arrowSwingDistanceDp is arrow right and left swing distance
 * @param arrowSwingDurationMs is single arrow swing animation duration
 * @param arrowSwingSlowdownMultiplier
 * * is arrow swing animation duration slowdown speed
 * - The smaller the value, the later it slows down
 * - The larger the value, the faster it slows down
 */
data class ArrowStyle(
    @DrawableRes val arrowId: Int = R.drawable.ic_top_arrow,
    val arrowSizeDp: Dp = 48.dp,
    val arrowColor: Color = Color.Unspecified,
    val arrowPosition: ArrowPosition = ArrowPosition.TOP,
    val arrowOffsetY: Dp = 0.dp,
    val arrowAnimationStatus: Boolean = true,
    val arrowSwingDistanceDp: Dp = 10.dp,
    val arrowSwingDurationMs: Int = 50,
    val arrowSwingSlowdownMultiplier: Float = 0.1F
) : Serializable
