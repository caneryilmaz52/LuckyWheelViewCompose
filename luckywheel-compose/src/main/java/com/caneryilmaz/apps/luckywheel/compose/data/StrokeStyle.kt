package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.Serializable

/**
 * @param drawStroke is enable or disable wheel corner stroke drawing
 * @param strokeColor
 * * * is color of stroke line
 *  * - if [strokeColor] size = 1 then gradient stroke color disable and stroke color will be value of `strokeColor[0]`
 *  * - if [strokeColor] size > 1 then gradient stroke color enable
 *  * - if [strokeColor] is empty then gradient stroke color disable and stroke color will be [Color.Black]
 * @param strokeThicknessDp is thickness of item stroke circle
 */
data class StrokeStyle(
    val drawStroke: Boolean = false,
    val strokeColor: List<Color> = listOf(Color.Black),
    val strokeThicknessDp: Dp = 10.dp
) : Serializable
