package com.github.flatspike.compose.color.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color

@Stable
class ColorTextState(
    initialColor: Color,
    private val mapper: ColorTextMapper
) {

    private var _color by mutableStateOf(initialColor)
    private var _text by mutableStateOf(mapper.toText(initialColor, ""))

    var color: Color
        get() = _color
        set(color) {
            if (color == _color) return
            _color = color
            _text = mapper.toText(color, _text)
        }

    var text: String
        get() = _text
        set(text) {
            if (text == _text || !mapper.isValid(text)) return
            _color = mapper.toColor(text, _color)
            _text = text
        }

    companion object {

        fun Saver(mapper: ColorTextMapper) = Saver<ColorTextState, Long>(
            save = { it.color.value.toLong() },
            restore = { ColorTextState(Color(it), mapper) }
        )
    }
}

@Composable
fun rememberColorTextState(
    initialColor: Color,
    mapper: ColorTextMapper
): ColorTextState = rememberSaveable(mapper, saver = ColorTextState.Saver(mapper)) {
    ColorTextState(initialColor, mapper)
}

@Composable
fun rememberColorTextState(
    colorState: ColorState,
    mapper: ColorTextMapper
): ColorTextState {
    val colorTextState = rememberColorTextState(colorState.color, mapper)
    LaunchedEffect(colorState) {
        snapshotFlow { colorState.color }
            .collect { colorTextState.color = it }
    }
    LaunchedEffect(colorState) {
        snapshotFlow { colorTextState.color }
            .collect { colorState.color = it }
    }
    return colorTextState
}
