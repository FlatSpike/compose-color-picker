package com.github.flatspike.color.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@Stable
class ColorState(
    initialColor: Color,
    private val onColorChange: (Color) -> Unit = {},
    private val onHsvChange: (Hsv) -> Unit = {}
) {

    private var colorState by mutableStateOf(initialColor)

    private var hsvState by mutableStateOf(color.hsv)

    var color: Color
        get() = colorState
        set(color) {
            // skip hsv change on black color, otherwise it will be discarded
            // when alpha change and value equals 0 (in other words when color is black)
            if (color.toArgb() shl 16 != 0) {
                hsvState = color.hsv
            }
            colorState = color
            onHsvChange(hsv)
            onColorChange(color)
        }

    var hsv: Hsv
        get() = hsvState
        set(hsv) {
            val color = hsv.toColor(colorState.alpha)
            hsvState = hsv
            colorState = color
            onHsvChange(hsv)
            onColorChange(color)
        }

    companion object {

        fun Saver(
            onColorChanged: (Color) -> Unit = {},
            onHsvChanged: (Hsv) -> Unit = {}
        ) = Saver<ColorState, Long>(
            { it.color.value.toLong() },
            { ColorState(Color(it), onColorChanged, onHsvChanged) }
        )
    }
}

@Composable
fun rememberColorState(
    initialColor: Color,
    onColorChanged: (Color) -> Unit = {},
    onHsvChanged: (Hsv) -> Unit = {}
): ColorState {
    val onColorChangedState = rememberUpdatedState(onColorChanged)
    val onHsvChangedState = rememberUpdatedState(onHsvChanged)
    return rememberSaveable(
        saver = ColorState.Saver(onColorChangedState.value, onHsvChangedState.value)
    ) {
        ColorState(initialColor, onColorChanged, onHsvChanged)
    }
}
