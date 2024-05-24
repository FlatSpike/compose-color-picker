package com.github.flatspike.color.picker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val DefaultThumb = @Composable { color: Color ->
    ColorPickerDefaults.SliderThumb(color = color)
}

private val DefaultTrack = @Composable { color: Color ->
    ColorPickerDefaults.BlueTrack(color = color)
}

@Composable
fun BlueSlider(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    thumb: @Composable (Color) -> Unit = DefaultThumb,
    track: @Composable (Color) -> Unit = DefaultTrack
) {
    BlueSlider(
        color = colorState.color,
        onColorChange = { colorState.color = it },
        modifier = modifier,
        thumb = thumb,
        track = track
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlueSlider(
    color: Color,
    onColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    thumb: @Composable (Color) -> Unit = DefaultThumb,
    track: @Composable (Color) -> Unit = DefaultTrack
) {
    Slider(
        value = color.blue,
        onValueChange = { onColorChange(color.copy(blue = it)) },
        modifier = modifier,
        valueRange = 0f .. 1f,
        thumb = { thumb(color) },
        track = { track(color) }
    )
}

@Preview(showBackground = true)
@Composable
private fun BlueSliderPreview() {
    BlueSlider(
        colorState = rememberColorState(initialColor = Color.Blue.copy(blue = 0.5f))
    )
}
