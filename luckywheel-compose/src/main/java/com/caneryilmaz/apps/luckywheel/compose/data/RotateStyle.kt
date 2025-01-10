package com.caneryilmaz.apps.luckywheel.compose.data

import com.caneryilmaz.apps.luckywheel.compose.constant.RotationDirection
import com.caneryilmaz.apps.luckywheel.compose.constant.RotationSpeed
import java.io.Serializable

/**
 * @param rotateTimeMs is wheel rotate duration
 * @param rotateSpeed is wheel rotate speed [RotationSpeed.FAST], [RotationSpeed.NORMAL] or [RotationSpeed.SLOW]
 * @param rotateSpeedMultiplier is wheel rotate speed multiplier
 * @param stopCenterOfItem
 * * if `true` the arrow points to the center of the slice
 * - if `false` the arrow points to a random point on the slice.
 * @param rotationDirection is wheel rotate direction [RotationDirection.CLOCKWISE], [RotationDirection.COUNTER_CLOCKWISE]
 */
data class RotateStyle(
    val rotateTimeMs: Int = 5000,
    val rotateSpeed: RotationSpeed = RotationSpeed.NORMAL,
    val rotateSpeedMultiplier: Float = 1F,
    val stopCenterOfItem: Boolean = false,
    val rotationDirection: RotationDirection = RotationDirection.CLOCKWISE
) : Serializable
