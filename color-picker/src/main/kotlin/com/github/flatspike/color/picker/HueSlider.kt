package com.github.flatspike.color.picker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val DefaultThumb = @Composable { hsv: Hsv ->
    ColorPickerDefaults.SliderThumb(color = hsv.copy(value = 1f).toColor())
}

private val DefaultTrack = @Composable { _: Hsv ->
    ColorPickerDefaults.HueTrack()
}

@Composable
fun HueSlider(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb,
    track: @Composable (Hsv) -> Unit = DefaultTrack
) {
    HueSlider(
        hsv = colorState.hsv,
        onHsvChange = { colorState.hsv = it },
        modifier = modifier,
        thumb = thumb,
        track = track
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HueSlider(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    modifier: Modifier = Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb,
    track: @Composable (Hsv) -> Unit = DefaultTrack
) {
    Slider(
        value = hsv.hue,
        onValueChange = { onHsvChange(hsv.copy(hue = it)) },
        modifier = modifier,
        valueRange = 0f .. 360f,
        thumb = { thumb(hsv) },
        track = { track(hsv) }
    )
}

@Preview(showBackground = true)
@Composable
private fun HueSliderPreview() {
    HueSlider(
        colorState = rememberColorState(initialColor = Color.Cyan)
    )
}
