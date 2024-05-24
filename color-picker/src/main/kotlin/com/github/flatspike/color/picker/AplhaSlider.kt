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
    ColorPickerDefaults.AlphaTrack(color = color)
}

@Composable
fun AlphaSlider(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    thumb: @Composable (Color) -> Unit = DefaultThumb,
    track: @Composable (Color) -> Unit = DefaultTrack
) {
    AlphaSlider(
        color = colorState.color,
        onColorChange = { colorState.color = it },
        modifier = modifier,
        thumb = thumb,
        track = track
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlphaSlider(
    color: Color,
    onColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    thumb: @Composable (Color) -> Unit = DefaultThumb,
    track: @Composable (Color) -> Unit = DefaultTrack
) {
    Slider(
        value = color.alpha,
        onValueChange = { onColorChange(color.copy(alpha = it)) },
        modifier = modifier,
        valueRange = 0f .. 1f,
        thumb = { thumb(color) },
        track = { track(color) }
    )
}

@Preview(showBackground = true)
@Composable
private fun AlphaSliderPreview() {
    AlphaSlider(
        colorState = rememberColorState(initialColor = Color.Black.copy(alpha = 0.5f))
    )
}
