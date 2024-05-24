package com.github.flatspike.color.picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.offset
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

private val DefaultThumb = @Composable { hsv: Hsv ->
    ColorPickerDefaults.PaletteThumb(color = hsv.copy(value = 1f).toColor())
}

@Composable
fun CirclePalette(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb
) {
    CirclePalette(
        hsv = colorState.hsv,
        onHsvChange = { colorState.hsv = it },
        modifier = modifier,
        thumb = thumb
    )
}

@Composable
fun CirclePalette(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    modifier: Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb
) {
    Layout(
        content = {
            thumb(hsv)
            Circle(hsv = hsv, onHsvChange = onHsvChange)
        },
        modifier = modifier
    ) { measurables, constrains ->
        val thumbPlaceable = measurables[0].measure(constrains)
        val circlePlaceable = measurables[1].measure(
            constrains.offset(
                horizontal = -thumbPlaceable.width,
                vertical = -thumbPlaceable.height
            ).copy(minWidth = 0, minHeight = 0)
        )

        val circleOffsetX = thumbPlaceable.width / 2f
        val circleOffsetY = thumbPlaceable.height / 2f

        val thumbHueRadians = Math.toRadians(360.0 - hsv.hue)
        val thumbDistance = minOf(circlePlaceable.width, circlePlaceable.height) / 2f
        val thumbOffsetX = (
            thumbDistance * hsv.saturation * cos(thumbHueRadians) +
            circlePlaceable.width / 2f
        )
        val thumbOffsetY = (
            thumbDistance * hsv.saturation * sin(thumbHueRadians) +
            circlePlaceable.height / 2f
        )

        val paletteWidth = thumbPlaceable.width + circlePlaceable.width
        val paletteHeight = thumbPlaceable.height + circlePlaceable.height

        layout(paletteWidth, paletteHeight) {
            circlePlaceable.placeRelative(circleOffsetX.toInt(), circleOffsetY.toInt())
            thumbPlaceable.placeRelative(thumbOffsetX.toInt(), thumbOffsetY.toInt())
        }
    }
}

@Composable
private inline fun Circle(
    hsv: Hsv,
    crossinline onHsvChange: (Hsv) -> Unit,
) {
    val hsvState by rememberUpdatedState(hsv)
    Canvas(
        modifier = Modifier
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onHsvChange(calculateHsv(offset, size, hsvState))
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onHsvChange(calculateHsv(change.position, size, hsvState))
                }
            }
    ) {
        drawCircle(
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
            )
        )

        drawCircle(
            Brush.radialGradient(
                listOf(
                    Color.White,
                    Color.Transparent
                )
            )
        )
    }
}

private fun calculateHsv(offset: Offset, size: IntSize, origin: Hsv): Hsv {
    val width = size.width.toFloat()
    val height = size.height.toFloat()
    val x = (offset.x - width / 2).toDouble()
    val y = (offset.y - height / 2).toDouble()
    val radius = minOf(width, height) / 2
    val hue = (360 - (atan2(y, x) * 180 / Math.PI)) % 360
    val saturation = hypot(x, y) / radius
    return origin.copy(hue = hue.toFloat(), saturation = minOf(saturation.toFloat(), 1f))
}

@Preview(showBackground = true)
@Composable
private fun CirclePalettePreview() {
    CirclePalette(
        colorState = rememberColorState(initialColor = Color.White)
    )
}