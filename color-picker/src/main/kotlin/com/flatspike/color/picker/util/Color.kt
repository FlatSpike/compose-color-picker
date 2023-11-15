package com.flatspike.color.picker.util

import androidx.compose.ui.graphics.Color

val Color.redInt get() = (red * 255).toInt()

val Color.greenInt get() = (green * 255).toInt()

val Color.blueInt get() = (blue * 255).toInt()

val Color.alphaInt get() = (alpha * 100).toInt()

