package com.caneryilmaz.apps.luckywheel.compose.state

import androidx.compose.runtime.Immutable
import java.io.Serializable

/**
 * @param startRotate is start wheel rotation
 * @see [Immutable]
 */
@Immutable
data class WheelViewState(
    val startRotate: Boolean = false
) : Serializable