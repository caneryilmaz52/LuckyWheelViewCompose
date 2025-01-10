package com.caneryilmaz.apps.luckywheel.compose.data

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
 */
data class ItemTextStyle(
    val textOrientation: TextOrientation = TextOrientation.HORIZONTAL,
    val textPaddingDp: Dp = 4.dp,
    val textSizeSp: TextUnit = 16.sp,
    val letterSpacingSp: TextUnit = TextUnit.Unspecified,
    @FontRes val textFontId: Int? = null
) : Serializable
