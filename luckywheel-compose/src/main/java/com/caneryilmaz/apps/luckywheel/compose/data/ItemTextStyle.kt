package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.annotation.FloatRange
import androidx.annotation.FontRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caneryilmaz.apps.luckywheel.compose.constant.TextOrientation
import java.io.Serializable

/**
 * @param textOrientation is text orientation of wheel items [TextOrientation.HORIZONTAL] or [TextOrientation.VERTICAL]
 * @param textPaddingDp is text padding from wheel corner
 * @param textSizeSp is text size of wheel items
 * @param letterSpacingSp is letter spacing of wheel items text
 * @param textFontId is custom font resource id of wheel items text
 * @param textPositionFraction
 *  * is text vertical position fraction in wheel slice
 *  * - The smaller the value, the closer to the center
 *  * - The larger the value, the closer to the corners
 */
data class ItemTextStyle(
    val textOrientation: TextOrientation = TextOrientation.HORIZONTAL,
    val textPaddingDp: Dp = 4.dp,
    val textSizeSp: TextUnit = 16.sp,
    val letterSpacingSp: TextUnit = TextUnit.Unspecified,
    @FontRes val textFontId: Int? = null,
    @FloatRange(from = 0.1, to = 0.9) val textPositionFraction: Float = 0.7F
) : Serializable
