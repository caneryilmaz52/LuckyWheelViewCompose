package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.annotation.FontRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import java.io.Serializable

/**
 * @param text is wheel item text
 * @param textColor
 * * is color of item text
 * - if [textColor] size = 1 then gradient text color disable and text color will be value of `textColor[0]`
 * - if [textColor] size > 1 then gradient text color enable
 * - if [textColor] is empty then wheel view is not drawn
 * @param backgroundColor
 * * is background color of item
 * - if [backgroundColor] size = 1 then gradient background color disable and background color will be value of `backgroundColor[0]`
 * - if [backgroundColor] size > 1 then gradient background color enable
 * - if [backgroundColor] is empty then wheel view is not drawn
 * @param textFontId
 * * is custom font id of item text
 * - if [textFontId] is not null then [ItemTextStyle.textFontId] will be overridden for this item text
 * @param icon is item icon resource id, if not null then icon will be drawn
 * @param iconColor is icon tint color
 */
data class WheelData(
    val text: String,
    val textColor: List<Color>,
    val backgroundColor: List<Color>,
    @FontRes val textFontId: Int? = null,
    val icon: ImageBitmap? = null,
    val iconColor: Color = Color.Unspecified
) : Serializable