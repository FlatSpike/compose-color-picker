package com.flatspike.color.picker.hsv

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.flatspike.color.picker.util.clipRoundRect
import com.flatspike.color.picker.util.drawSliderHandle
import kotlin.math.max
import kotlin.math.min

@Composable
fun HsvHueSlider(
    state: HsvColorState,
    modifier: Modifier = Modifier,
    handle: DrawScope.(Offset, Color) -> Unit = defaultHandle
) {
    HsvHueSlider(
        hsv = state.hsv,
        onHsvChange = { state.hsv = it },
        modifier = modifier,
        handle = handle
    )
}

@Composable
fun HsvHueSlider(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    modifier: Modifier = Modifier,
    handle: DrawScope.(Offset, Color) -> Unit = defaultHandle
) {
    Box(modifier = modifier.height(48.dp)) {
        val hsvState = rememberUpdatedState(hsv)
        val onHsvChangeState = rememberUpdatedState(onHsvChange)
        val handleState = rememberUpdatedState(handle)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
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
            clipRoundRect(cornerRadius = CornerRadius(16f, 16f)) {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green,
                            Color.Cyan,
                            Color.Blue,
                            Color.Magenta,
                            Color.Red
                        )
                    )
                )
            }

            handleState.value.invoke(
                this,
                hsvState.value.toOffset(size),
                hsvState.value.copy(value = 1f, saturation = 1f).toColor()
            )
        }
    }
}

private val defaultHandle: DrawScope.(Offset, Color) -> Unit = { offset, color ->
    drawSliderHandle(offset, color)
}

private fun Offset.toHsv(size: IntSize, origin: Hsv): Hsv = toHsv(size.toSize(), origin)

private fun Offset.toHsv(size: Size, origin: Hsv): Hsv =
    origin.copy(hue = max(0f, min(x / size.width * 360f,360f)))

private fun Hsv.toOffset(size: Size): Offset = Offset(size.width * hue / 360f, size.height / 2)

@Preview(showBackground = true)
@Composable
private fun HsvHueSliderPreview() {
    var hsv by remember { mutableStateOf(Hsv(hue = 180f)) }
    HsvHueSlider(
        hsv = hsv,
        onHsvChange = { hsv = it }
    )
}
