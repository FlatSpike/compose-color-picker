package com.flatspike.color.picker.hsv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color

@Stable
class HsvColorState(
    initialColor: Color,
    private val onColorChanged: (Color) -> Unit = {}
) {

    private val _color = mutableStateOf(initialColor)

    private val _hsv = mutableStateOf(initialColor.hsv)

    var color get() = _color.value
        set(value) {
            _hsv.value = value.hsv
            _color.value = value
            onColorChanged(_color.value)
        }

    var hsv get() = _hsv.value
        set(value) {
            _hsv.value = value
            _color.value = value.toColor(_color.value.alpha)
            onColorChanged(_color.value)
        }

    companion object {

        fun Saver(
            onColorChanged: (Color) -> Unit = {}
        ) = Saver<HsvColorState, Long>(
            save = { it.color.value.toLong() },
            restore = { HsvColorState(Color(it.toULong()), onColorChanged) }
        )
    }
}

@Composable
fun rememberHsvColorState(
    initialColor: Color,
    onColorChanged: (Color) -> Unit = {}
): HsvColorState {
    val onColorChangedState = rememberUpdatedState(onColorChanged)
    return rememberSaveable(saver = HsvColorState.Saver(onColorChangedState.value)) {
        HsvColorState(initialColor, onColorChanged)
    }
}
