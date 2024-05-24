package com.github.flatspike.color.picker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val DefaultThumb = @Composable { color: Color ->
    ColorPickerDefaults.SliderThumb(color = color)
}

private val DefaultTrack = @Composable { color: Color ->
    ColorPickerDefaults.RedTrack(color = color)
}

@Composable
fun RedSlider(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    thumb: @Composable (Color) -> Unit = DefaultThumb,
    track: @Composable (Color) -> Unit = DefaultTrack
) {
    RedSlider(
        color = colorState.color,
        onColorChange = { colorState.color = it },
        modifier = modifier,
        thumb = thumb,
        track = track
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedSlider(
    color: Color,
    onColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    thumb: @Composable (Color) -> Unit = DefaultThumb,
    track: @Composable (Color) -> Unit = DefaultTrack
) {
    Slider(
        value = color.red,
        onValueChange = { onColorChange(color.copy(red = it)) },
        modifier = modifier,
        valueRange = 0f .. 1f,
        thumb = { thumb(color) },
        track = { track(color) }
    )
}

@Preview(showBackground = true)
@Composable
private fun RedSliderPreview() {
    RedSlider(
        colorState = rememberColorState(initialColor = Color.Red.copy(red = 0.5f))
    )
}
