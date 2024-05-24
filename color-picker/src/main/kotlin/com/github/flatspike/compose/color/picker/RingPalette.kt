package com.github.flatspike.compose.color.picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

private val DefaultRingWidth = 42.dp
private val DefaultMinSize = 64.dp

private val DefaultRingThumb = @Composable { hsv: Hsv ->
    ColorPickerDefaults.SliderThumb(color = hsv.copy(saturation = 1f, value = 1f).toColor())
}

private val DefaultTriangleThumb = @Composable { hsv: Hsv ->
    ColorPickerDefaults.PaletteThumb(color = hsv.toColor())
}

@Composable
fun RingPalette(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    ringThumb: @Composable (Hsv) -> Unit = DefaultRingThumb,
    triangleThumb: @Composable (Hsv) -> Unit = DefaultTriangleThumb
) {
    RingPalette(
        hsv = colorState.hsv, 
        onHsvChange = { colorState.hsv = it },
        modifier = modifier,
        ringThumb = ringThumb,
        triangleThumb = triangleThumb
    )
}

@Composable
fun RingPalette(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    modifier: Modifier = Modifier,
    ringWidth: Dp = DefaultRingWidth,
    ringThumb: @Composable (Hsv) -> Unit = DefaultRingThumb,
    triangleThumb: @Composable (Hsv) -> Unit = DefaultTriangleThumb
) {
    Layout(
        content = {
            Box(modifier = Modifier.rotate(90f - hsv.hue)) {
                ringThumb(hsv)
            }
            Box {
                Ring(
                    hsv = hsv,
                    onHsvChange = onHsvChange,
                    width = ringWidth
                )
            }
            Box(modifier = Modifier.rotate(360f - hsv.hue)) {
                TriangleLayout(
                    hsv = hsv,
                    onHsvChange = onHsvChange,
                    thumb = triangleThumb
                )
            }
        },
        modifier = modifier
            .requiredSizeIn(
                minWidth = DefaultMinSize,
                minHeight = DefaultMinSize
            )
    ) { measurables, constrains ->
        val ringWidthPx = ringWidth.toPx()
        val thumbPlaceable = measurables[0].measure(
            constrains.copy(minWidth = 0, minHeight = 0)
        )

        val ringOffset = (thumbPlaceable.height - ringWidthPx).coerceAtLeast(0f)
        val ringPlaceable = measurables[1].measure(
            constrains.offset(
                horizontal = -ringOffset.toInt(),
                vertical = -ringOffset.toInt()
            ).copy(minWidth = 0, minHeight = 0)
        )

        val hueRadians = Math.toRadians(360.0 - hsv.hue)
        val thumbTrackRadius = (ringPlaceable.width - ringWidthPx) / 2f
        val thumbOffsetX = (
            thumbTrackRadius * cos(hueRadians) +
            ringPlaceable.width / 2f + ringOffset - thumbPlaceable.width / 2f
        )
        val thumbOffsetY = (
            thumbTrackRadius * sin(hueRadians) +
            ringPlaceable.height / 2 + ringOffset - thumbPlaceable.height / 2f
        )

        val triangleSize = (
            ringPlaceable.width - ringWidthPx * 2f - ringOffset
        ).coerceAtLeast(0f)
        val trianglePlaceable = measurables[2].measure(
            Constraints(
                maxWidth = triangleSize.toInt(),
                maxHeight = triangleSize.toInt()
            )
        )
        val triangleOffset = ringOffset + ringWidthPx + ringOffset / 2

        val paletteWidth = ringPlaceable.width + ringOffset * 2f
        val paletteHeight = ringPlaceable.height + ringOffset * 2f

        layout(paletteWidth.toInt(), paletteHeight.toInt()) {
            ringPlaceable.placeRelative(
                ringOffset.toInt(),
                ringOffset.toInt()
            )
            thumbPlaceable.placeRelative(
                thumbOffsetX.toInt(),
                thumbOffsetY.toInt()
            )
            trianglePlaceable.placeRelative(
                triangleOffset.toInt(),
                triangleOffset.toInt()
            )
        }
    }
}

@Composable
private inline fun Ring(
    hsv: Hsv,
    crossinline onHsvChange: (Hsv) -> Unit,
    width: Dp = DefaultRingWidth
) {
    val hsvState by rememberUpdatedState(hsv)
    val widthPx = with(LocalDensity.current) { width.toPx() }
    Canvas(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(GenericShape { size, _ ->
                val radius = size.minDimension / 2
                addRing(Rect(size.center, radius), widthPx)
            })
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onHsvChange(calculateRingHsv(offset, size, hsvState))
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onHsvChange(calculateRingHsv(change.position, size, hsvState))
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
            widthPx
        )
    }
}

private fun calculateRingHsv(offset: Offset, size: IntSize, origin: Hsv): Hsv {
    val width = size.width.toFloat()
    val height = size.height.toFloat()
    val x = (offset.x - width / 2).toDouble()
    val y = (offset.y - height / 2).toDouble()
    val hue = (360 - (atan2(y, x) * 180 / Math.PI)) % 360
    return origin.copy(hue = hue.toFloat())
}

@Composable
private fun TriangleLayout(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    thumb: @Composable (Hsv) -> Unit
) {
    Layout(
        content = {
            thumb(hsv)
            Triangle(
                hsv = hsv,
                onHsvChange = onHsvChange
            )
        }
    ) { measurables, constrains ->
        val thumbPlaceable = measurables[0].measure(
            constrains.copy(minWidth = 0, minHeight = 0)
        )

        val triangleOffset = maxOf(thumbPlaceable.width, thumbPlaceable.height) / 2f
        val trianglePlaceable = measurables[1].measure(
            constrains.offset(
                horizontal = -(triangleOffset * 2).toInt(),
                vertical = -(triangleOffset * 2).toInt()
            )
        )
        val triangleMinDimension = minOf(trianglePlaceable.width, trianglePlaceable.height)
        val triangleRadius = triangleMinDimension / 2f
        val triangleWidth = trianglePlaceable.width + triangleOffset * 2
        val triangleHeight = trianglePlaceable.height + triangleOffset * 2

        val thumbSaturation = TriangleConst.RAD_60 * hsv.saturation
        val thumbDistance = (
            (triangleRadius * 1.5f) /
            cos(thumbSaturation - TriangleConst.RAD_30)
        ) * hsv.value
        val thumbAngle = TriangleConst.RAD_90 - thumbSaturation
        val thumbOffsetX = (
            thumbDistance * cos(thumbAngle) +
            triangleRadius + (triangleRadius * TriangleConst.COS_240) -
            thumbPlaceable.width / 2
        ) + triangleOffset
        val thumbOffsetY = (
            thumbDistance * sin(thumbAngle) +
            triangleRadius + (triangleRadius * TriangleConst.SIN_240) -
            thumbPlaceable.height / 2
        ) + triangleOffset

        layout(triangleWidth.toInt(), triangleHeight.toInt()) {
            trianglePlaceable.placeRelative(
                triangleOffset.toInt(),
                triangleOffset.toInt()
            )
            thumbPlaceable.placeRelative(
                thumbOffsetX.toInt(),
                thumbOffsetY.toInt()
            )
        }
    }
}

@Composable
private inline fun Triangle(
    hsv: Hsv,
    crossinline onHsvChange: (Hsv) -> Unit
) {
    val hsvState by rememberUpdatedState(hsv)
    Canvas(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(GenericShape { size, _ ->
                addEquilateralTriangle(size.center, size.minDimension / 2)
            })
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onHsvChange(calculateTriangleHsv(offset, size, hsvState))
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onHsvChange(calculateTriangleHsv(change.position, size, hsvState))
                }
            }
    ) {
        val center = size.center
        val radius = size.maxDimension / 2
        val triangle = Path()
        val (point1, point2, point3) = triangle.addEquilateralTriangle(center, radius)
        val innerRadius = radius / 2
        drawPath(
            triangle,
            Brush.linearGradient(
                listOf(hsvState.copy(saturation = 1f, value = 1f).toColor(), Color.Transparent),
                point1,
                Offset(
                    center.x + innerRadius * TriangleConst.COS_180,
                    center.y + innerRadius * TriangleConst.SIN_180
                )
            )
        )
        drawPath(
            triangle,
            Brush.linearGradient(
                listOf(Color.White, Color.Transparent),
                point2,
                Offset(
                    center.x + innerRadius * TriangleConst.COS_300,
                    center.y + innerRadius * TriangleConst.SIN_300
                )
            )
        )
        drawPath(
            triangle,
            Brush.linearGradient(
                listOf(Color.Black, Color.Transparent),
                point3,
                Offset(
                    center.x + innerRadius * TriangleConst.COS_60,
                    center.y + innerRadius * TriangleConst.SIN_60
                )
            )
        )
    }
}

private fun calculateTriangleHsv(offset: Offset, size: IntSize, origin: Hsv): Hsv {
    val diameter = maxOf(size.width, size.height).toFloat()
    val radius = diameter / 2f
    val center = Offset(size.width / 2f, size.height / 2f)
    val start = Offset(
        center.x + radius * TriangleConst.COS_240,
        center.y + radius * TriangleConst.SIN_240
    )
    val diff = offset - start
    val angle = TriangleConst.RAD_90 - atan2(diff.y, diff.x)
    val saturation = (angle / TriangleConst.RAD_60).coerceIn(0f, 1f)
    val value = (
        hypot(start.x - offset.x, start.y - offset.y) /
        (1.5f * radius / cos(angle - TriangleConst.RAD_30))
    ).coerceIn(0f, 1f)
    return origin.copy(saturation = saturation, value = value)
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun RingPalettePreview() {
    RingPalette(
        colorState = rememberColorState(initialColor = Color.Red)
    )
}
