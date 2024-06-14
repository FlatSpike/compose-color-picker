package com.github.flatspike.compose.color.picker.util

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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import kotlin.math.ceil

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
