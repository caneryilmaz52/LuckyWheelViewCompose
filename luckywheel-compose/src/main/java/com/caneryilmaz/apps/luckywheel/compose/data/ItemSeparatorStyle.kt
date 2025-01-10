package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.Serializable

/**
 * @param drawItemSeparator is enable or disable wheel item separator drawing
 * @param itemSeparatorColor
 * * is color of item separator line
 * - if [itemSeparatorColor] size = 1 then gradient separator color disable and separator color will be value of `itemSeparatorColor[0]`
 * - if [itemSeparatorColor] size > 1 then gradient separator color enable
 * - if [itemSeparatorColor] is empty then gradient separator color disable and separator color will be [Color.Black]
 * @param itemSeparatorThicknessDp is thickness of item separator line
 */
data class ItemSeparatorStyle(
    val drawItemSeparator: Boolean = false,
    val itemSeparatorColor: List<Color> = listOf(Color.Black),
    val itemSeparatorThicknessDp: Dp = 4.dp
) : Serializable
