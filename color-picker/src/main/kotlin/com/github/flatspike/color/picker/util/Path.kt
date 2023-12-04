package com.github.flatspike.color.picker.util

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation

fun Path.addRing(
    rect: Rect,
    width: Float
) {
    op(
        path1 = Path().apply { addOval(rect) },
        path2 = Path().apply { addOval(rect.deflate(width)) },
        operation = PathOperation.Difference
    )
}
