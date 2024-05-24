@file:Suppress("unused")

package com.github.flatspike.compose.color.picker

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.luminance
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

internal fun Color.borderColor(
    luminanceThreshold: Float = 0.8f
) = if (luminance() < luminanceThreshold) Color.White else Color.Black

internal fun Size.offsetSize(offset: Offset): Size =
    Size(this.width - offset.x, this.height - offset.y)

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

internal inline fun DrawScope.clipRoundRect(
    roundRect: RoundRect,
    clipOp: ClipOp = ClipOp.Intersect,
    block: DrawScope.() -> Unit
) {
    clipPath(Path().apply { addRoundRect(roundRect) }, clipOp, block)
}

internal inline fun DrawScope.clipRoundRect(
    topLeft: Offset = Offset.Zero,
    size: Size = this.size.offsetSize(topLeft),
    cornerRadius: CornerRadius = CornerRadius.Zero,
    clipOp: ClipOp = ClipOp.Intersect,
    block: DrawScope.() -> Unit
) {
    clipRoundRect(RoundRect(size.toRect(), cornerRadius), clipOp, block)
}

internal fun DrawScope.drawCheckerboard(
    tileSize: Size = Size(24f, 24f),
    primaryColor: Color = Color.White,
    secondaryColor: Color = Color.Gray,
    topLeft: Offset = Offset.Zero,
    size: Size = this.size.offsetSize(topLeft),
) {
    clipRect(topLeft.x, topLeft.y, topLeft.x + size.width, topLeft.y + size.height) {
        val cols = ceil(size.width / tileSize.width).toInt()
        val rows = ceil(size.height / tileSize.height).toInt()
        for (col in 0..cols) {
            for (row in 0..rows) {
                val color = if ((col + row) % 2 == 0) primaryColor else secondaryColor
                val offset = Offset(col * tileSize.width, row * tileSize.height)
                drawRect(color, offset, tileSize)
            }
        }
    }
}

internal fun DrawScope.drawRing(
    color: Color,
    width: Float,
    radius: Float = size.minDimension / 2.0f,
    center: Offset = this.center,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {
    drawPath(
        Path().apply { addRing(Rect(center, radius), width) },
        color,
        alpha,
        style,
        colorFilter,
        blendMode
    )
}

internal fun DrawScope.drawRing(
    brush: Brush,
    width: Float,
    radius: Float = size.minDimension / 2.0f,
    center: Offset = this.center,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {
    drawPath(
        Path().apply { addRing(Rect(center, radius), width) },
        brush,
        alpha,
        style,
        colorFilter,
        blendMode
    )
}

internal fun Path.addRing(rect: Rect, width: Float) {
    op(
        path1 = Path().apply { addOval(rect) },
        path2 = Path().apply { addOval(rect.deflate(width)) },
        operation = PathOperation.Difference
    )
}

@Suppress("MemberVisibilityCanBePrivate")
internal object TriangleConst {
    val RAD_30 = Math.toRadians(30.0).toFloat()
    val RAD_60 = Math.toRadians(60.0).toFloat()
    val RAD_90 = Math.toRadians(90.0).toFloat()
    val RAD_120 = Math.toRadians(120.0).toFloat()
    val RAD_180 = Math.toRadians(180.0).toFloat()
    val RAD_240 = Math.toRadians(240.0).toFloat()
    val RAD_300 = Math.toRadians(300.0).toFloat()

    val SIN_60 = sin(RAD_60)
    val SIN_120 = sin(RAD_120)
    val SIN_180 = sin(RAD_180)
    val SIN_240 = sin(RAD_240)
    val SIN_300 = sin(RAD_300)

    val COS_60 = cos(RAD_60)
    val COS_120 = cos(RAD_120)
    val COS_180 = cos(RAD_180)
    val COS_240 = cos(RAD_240)
    val COS_300 = cos(RAD_300)
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