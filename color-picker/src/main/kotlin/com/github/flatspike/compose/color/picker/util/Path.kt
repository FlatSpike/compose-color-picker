package com.github.flatspike.compose.color.picker.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation

internal fun Path.addRing(rect: Rect, width: Float) {
    op(
        path1 = Path().apply { addOval(rect) },
        path2 = Path().apply { addOval(rect.deflate(width)) },
        operation = PathOperation.Difference
    )
}

internal fun Path.addEquilateralTriangle(center: Offset, radius: Float): Array<Offset> {
    val points = equilateralTrianglePoints(center, radius)
    moveTo(points[0].x, points[0].y)
    lineTo(points[1].x, points[1].y)
    lineTo(points[2].x, points[2].y)
    return points
}

internal fun equilateralTrianglePoints(center: Offset, radius: Float): Array<Offset> = arrayOf(
    Offset(center.x + radius, center.y),
    Offset(center.x + radius * TriangleConst.COS_120, center.y + radius * TriangleConst.SIN_120),
    Offset(center.x + radius * TriangleConst.COS_240, center.y + radius * TriangleConst.SIN_240)
)
