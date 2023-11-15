package com.flatspike.color.picker.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

fun Size.offsetSize(offset: Offset): Size = Size(this.width - offset.x, this.height - offset.y)
