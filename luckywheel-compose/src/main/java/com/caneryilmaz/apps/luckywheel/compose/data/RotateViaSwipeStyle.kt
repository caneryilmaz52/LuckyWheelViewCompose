package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.Serializable

/**
 * @param rotateViaSwipe is enable or disable start wheel rotate via swipe down
 * @param rotateSwipeDistanceDp is swipe distance to start rotate wheel
 * @param allowRotateAgain is enable or disable allowing another rotate after first rotation completed
 */
data class RotateViaSwipeStyle(
    val rotateViaSwipe: Boolean = false,
    val rotateSwipeDistanceDp: Dp = 25.dp,
    val allowRotateAgain: Boolean = false
) : Serializable
