package com.github.flatspike.compose.color.picker.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

internal fun Offset.rotate(center: Offset, angle: Float): Offset {
    val dx = x - center.x
    val dy = y - center.y
    val cos = cos(angle)
    val sin = sin(angle)
    return Offset(
        center.x + dx * cos - dy * sin,
        center.y + dx * sin + dy * cos
    )
}
