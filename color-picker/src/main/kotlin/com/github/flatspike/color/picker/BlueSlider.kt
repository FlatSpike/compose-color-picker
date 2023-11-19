package com.github.flatspike.color.picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.github.flatspike.color.picker.util.clipRoundRect
import com.github.flatspike.color.picker.util.drawSliderHandle
import kotlin.math.max
import kotlin.math.min

@Composable
fun BlueSlider(
    color: Color,
    onColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    handle: DrawScope.(Offset, Color) -> Unit = { offset, selectedColor ->
        drawSliderHandle(offset, selectedColor)
    }
) {
    Box(modifier = modifier.height(48.dp)) {
        val colorState = rememberUpdatedState(color)
        val onColorChangeState = rememberUpdatedState(onColorChange)
        val handleState = rememberUpdatedState(handle)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        offset
                            .toColor(size, colorState.value)
                            .also(onColorChangeState.value)
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        change.position
                            .toColor(size, colorState.value)
                            .also(onColorChangeState.value)
                    }
                }
        ) {
            val maxAlphaColor = colorState.value.copy(alpha = 1f)

            clipRoundRect(cornerRadius = CornerRadius(16f, 16f)) {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            maxAlphaColor.copy(blue = 0f),
                            maxAlphaColor.copy(blue = 1f)
                        ),
                    )
                )
            }

            handleState.value.invoke(
                this,
                colorState.value.toOffset(size),
                maxAlphaColor
            )
        }
    }
}

private fun Offset.toColor(size: IntSize, origin: Color): Color = toColor(size.toSize(), origin)

private fun Offset.toColor(size: Size, origin: Color): Color =
    origin.copy(blue = max(0f, min(x / size.width,1f)))

private fun Color.toOffset(size: Size): Offset = Offset(size.width * blue, size.height / 2)

@Preview(showBackground = true)
@Composable
private fun RgbBlueSliderPreview() {
    var color by remember { mutableStateOf(Color.White) }
    BlueSlider(
        color = color,
        onColorChange = { color = it }
    )
}
