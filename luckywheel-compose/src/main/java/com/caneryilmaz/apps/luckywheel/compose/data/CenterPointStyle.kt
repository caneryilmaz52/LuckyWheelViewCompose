package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.Serializable

/**
 * @param drawCenterPoint is enable or disable center point drawing
 * @param centerPointColor is color of center point
 * @param centerPointRadiusDp is radius of center point
 */
data class CenterPointStyle(
    val drawCenterPoint: Boolean = false,
    val centerPointColor: Color = Color.White,
    val centerPointRadiusDp: Dp = 20.dp
) : Serializable
