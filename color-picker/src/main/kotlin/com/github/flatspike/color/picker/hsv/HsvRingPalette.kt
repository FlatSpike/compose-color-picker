package com.github.flatspike.color.picker.hsv

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize
import com.github.flatspike.color.picker.util.clipRoundRect
import com.github.flatspike.color.picker.util.drawPalettePointer
import com.github.flatspike.color.picker.util.drawRing
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.min
import kotlin.math.sin

@Composable
fun HsvRingPalette(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    modifier: Modifier = Modifier,
    pointer: DrawScope.(Offset, Color) -> Unit = defaultPointer
) {
    Box(modifier = modifier) {
        val hsvState = rememberUpdatedState(hsv)
        val onHsvChangeState = rememberUpdatedState(onHsvChange)
        val pointerState = rememberUpdatedState(pointer)

        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        offset
                            .toHsv(size, hsvState.value)
                            .also(onHsvChangeState.value)
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        change.position
                            .toHsv(size, hsvState.value)
                            .also(onHsvChangeState.value)
                    }
                }
        ) {
            drawRing(
                Brush.sweepGradient(
                    listOf(
                        Color.Red,
                        Color.Magenta,
                        Color.Blue,
                        Color.Cyan,
                        Color.Green,
                        Color.Yellow,
                        Color.Red
                    )
                ),
                size.minDimension / 2 * 0.2f
            )

            pointerState.value.invoke(
                this,
                hsvState.value.toOffset(size),
                hsvState.value.toColor()
            )
        }
    }
}

private val defaultPointer: DrawScope.(Offset, Color) -> Unit = { offset, color ->
    drawPalettePointer(offset, color = color)
}

private fun Offset.toHsv(size: IntSize, origin: Hsv): Hsv = toHsv(size.toSize(), origin)

private fun Offset.toHsv(size: Size, origin: Hsv): Hsv =
    (this - size.center).toHsv(size.minDimension / 2, origin)

private fun Offset.toHsv(radius: Float, origin: Hsv): Hsv {
    val hue = (360 - (atan2(y.toDouble(), x.toDouble()) * 180 / Math.PI)) % 360
    val saturation = hypot(x.toDouble(), y.toDouble()) / radius
    return origin.copy(hue = hue.toFloat(), saturation = min(saturation.toFloat(), 1f))
}

private fun Hsv.toOffset(size: Size): Offset {
    val rad = (360 - hue) * Math.PI / 180
    val radius = size.minDimension / 2
    val x = radius * saturation * cos(rad)
    val y = radius * saturation * sin(rad)
    val offset = Offset(x.toFloat(), y.toFloat())
    return offset + size.center
}

@Preview(showBackground = true)
@Composable
private fun HsvRingPalettePreview() {
    var hsv by remember { mutableStateOf(Hsv.White) }
    HsvRingPalette(
        hsv = hsv,
        onHsvChange = { hsv = it }
    )
}
