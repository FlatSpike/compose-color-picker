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


@Stable
class HsvTextState(
    initialHsv: Hsv,
    private val mapper: HsvTextMapper
) {

    private var _hsv by mutableStateOf(initialHsv)
    private var _text by mutableStateOf(mapper.toText(initialHsv, ""))

    var hsv: Hsv
        get() = _hsv
        set(hsv) {
            if (hsv == _hsv) return
            _hsv = hsv
            _text = mapper.toText(hsv, _text)
        }

    var text: String
        get() = _text
        set(text) {
            if (text == _text || !mapper.isValid(text)) return
            _hsv = mapper.toHsv(text, _hsv)
            _text = text
        }

    companion object {

        fun Saver(mapper: HsvTextMapper) = Saver<HsvTextState, Int>(
            save = { it.hsv.raw.toInt() },
            restore = { HsvTextState(Hsv(it), mapper) }
        )
    }
}

@Composable
fun rememberHsvTextState(
    initialHsv: Hsv,
    mapper: HsvTextMapper
): HsvTextState = rememberSaveable(mapper, saver = HsvTextState.Saver(mapper)) {
    HsvTextState(initialHsv, mapper)
}

@Composable
fun rememberHsvTextState(
    colorState: ColorState,
    mapper: HsvTextMapper
): HsvTextState {
    val hsvTextState = rememberHsvTextState(colorState.hsv, mapper)
    LaunchedEffect(colorState) {
        snapshotFlow { colorState.hsv }
            .collect { hsvTextState.hsv = it }
    }
    LaunchedEffect(colorState) {
        snapshotFlow { hsvTextState.hsv }
            .collect { colorState.hsv = it }
    }
    return hsvTextState
}
