package com.caneryilmaz.apps.luckywheel.compose.data

import java.io.Serializable

/**
 * @param rotateToRandomTarget
 * * default is `false`
 * - if `true` and [randomTargets] is null then win index will be randomly between `0` and `wheelItems.latsIndex`
 * - if `true` and [randomTargets] is not null then win index will be randomly one of members of [randomTargets] array
 * @param randomTargets is array of win index
 */
data class RandomRotateStyle(
    val rotateToRandomTarget: Boolean = false,
    val randomTargets: IntArray? = null,
) : Serializable
