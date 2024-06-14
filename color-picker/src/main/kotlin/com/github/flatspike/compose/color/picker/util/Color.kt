package com.github.flatspike.compose.color.picker.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb

internal fun Color.borderColor(
    luminanceThreshold: Float = 0.8f
) = if (luminance() < luminanceThreshold) Color.White else Color.Black

internal fun Color.toRgb() = toArgb() and 0xffffff