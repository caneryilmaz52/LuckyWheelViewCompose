package com.caneryilmaz.apps.luckywheel.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import java.io.Serializable

/**
 * @param image is [ImageBitmap] instance of center image
 * @param imageColor is center image tint color
 */
data class CenterImageStyle(
    val image: ImageBitmap? = null,
    val imageColor: Color = Color.Unspecified
) : Serializable
