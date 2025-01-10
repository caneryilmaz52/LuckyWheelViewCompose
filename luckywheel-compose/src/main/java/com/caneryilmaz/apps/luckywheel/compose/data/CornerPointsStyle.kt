package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.Serializable

/**
 * @param drawCornerPoints is enable or disable corner points drawing
 * @param cornerPointsEachSlice is count of point in a slice
 * @param cornerPointsColor
 * * is colors of corner points
 * - if [cornerPointsColor] is empty and [useRandomCornerPointsColor] is `false` then corner colors will be randomly
 * - if [cornerPointsColor] is not empty and [useRandomCornerPointsColor] is `true` then corner colors will be randomly
 * @param useRandomCornerPointsColor is enable or disable random corner points colors
 * @param useCornerPointsGlowEffect is enable or disable corner points glow effect
 * @param cornerPointsColorChangeSpeedMs is corner points color change duration
 * @param cornerPointsRadiusDp is radius of corner point
 */
data class CornerPointsStyle(
    val drawCornerPoints: Boolean = false,
    val cornerPointsEachSlice: Int = 1,
    val cornerPointsColor: MutableList<Color> = mutableListOf(),
    val useRandomCornerPointsColor: Boolean = true,
    val useCornerPointsGlowEffect: Boolean = true,
    val cornerPointsColorChangeSpeedMs: Int = 500,
    val cornerPointsRadiusDp: Dp = 4.dp
) : Serializable
