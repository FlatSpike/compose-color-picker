package com.flatspike.color.picker.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center

fun Offset.topLeft(size: Size): Offset = size.center.let { Offset(x - it.x, y - it.y) }
