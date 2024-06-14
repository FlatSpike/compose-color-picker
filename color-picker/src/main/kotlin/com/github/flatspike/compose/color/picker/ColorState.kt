package com.github.flatspike.compose.color.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.util.toRgb

@Stable
class ColorState(initialColor: Color) {

    private var _color by mutableStateOf(initialColor)
    private var _hsv by mutableStateOf(color.hsv)

    var color: Color
        get() = _color
        set(color) {
            if (color == _color) return
            // update hsv only if color`s rgb values changes
            if (color.toRgb() != _color.toRgb()) {
                _hsv = color.hsv
            }
            _color = color
        }

    var hsv: Hsv
        get() = _hsv
        set(hsv) {
            if (hsv == _hsv) return
            _hsv = hsv
            _color = hsv.toColor(_color.alpha)
        }

    companion object {

        val Saver = Saver<ColorState, Long>(
            { it.color.value.toLong() },
            { ColorState(Color(it)) }
        )
    }
}

@Composable
fun rememberColorState(initialColor: Color): ColorState =
    rememberSaveable(saver = ColorState.Saver) { ColorState(initialColor) }

@Composable
fun rememberColorState(initialHsv: Hsv): ColorState =
    rememberColorState(initialColor = initialHsv.toColor())
