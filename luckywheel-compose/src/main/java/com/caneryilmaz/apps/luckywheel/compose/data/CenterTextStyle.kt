package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.annotation.FontRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.io.Serializable

/**
 * @param text is center text value
 * @param textColor
 * * is color of center text
 * - if [textColor] size = 1 then gradient text color disable and text color will be value of `textColor[0]`
 * - if [textColor] size > 1 then gradient text color enable
 * - if [textColor] is empty then gradient text color disable and text color will be [Color.Black]
 * @param textSizeSp is size of center text
 * @param letterSpacingSp is letter spacing of center text
 * @param textFontId is custom font resource id of center text
 */
data class CenterTextStyle(
    val text: String,
    val textColor: List<Color> = listOf(Color.Black),
    val textSizeSp: TextUnit = 16.sp,
    val letterSpacingSp: TextUnit = TextUnit.Unspecified,
    @FontRes val textFontId: Int? = null
) : Serializable
