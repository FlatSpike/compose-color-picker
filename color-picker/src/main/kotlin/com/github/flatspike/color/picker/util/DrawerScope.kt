package com.github.flatspike.color.picker.util

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import kotlin.math.ceil

inline fun DrawScope.clipRoundRect(
    roundRect: RoundRect,
    clipOp: ClipOp = ClipOp.Intersect,
    block: DrawScope.() -> Unit
) {
    clipPath(Path().apply { addRoundRect(roundRect) }, clipOp, block)
}

inline fun DrawScope.clipRoundRect(
    topLeft: Offset = Offset.Zero,
    size: Size = this.size.offsetSize(topLeft),
    cornerRadius: CornerRadius = CornerRadius.Zero,
    clipOp: ClipOp = ClipOp.Intersect,
    block: DrawScope.() -> Unit
) {
    clipRoundRect(RoundRect(size.toRect(), cornerRadius), clipOp, block)
}

fun DrawScope.drawPalettePointer(
    offset: Offset,
    radius: Float = 36f,
    color: Color = Color.White
) {
    drawCircle(color, radius, offset)
    drawCircle(Color.White, radius, offset, style = Stroke(8f))
}

fun DrawScope.drawSliderHandle(
    offset: Offset,
    color: Color = Color.White,
    width: Float = 36f,
    stroke: Stroke = Stroke(8f)
) {
    val size = Size(width, size.height - stroke.width)
    val topLeft = offset.topLeft(size)
    val cornerRadius = CornerRadius(16f, 16f)
    drawRoundRect(color, topLeft, size, cornerRadius = cornerRadius)
    drawRoundRect(Color.White, topLeft, size, cornerRadius = cornerRadius, style = stroke)
}

fun DrawScope.drawCheckerboard(
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