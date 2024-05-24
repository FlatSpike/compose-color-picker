package com.github.flatspike.color.picker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val DefaultThumb = @Composable { hsv: Hsv ->
    ColorPickerDefaults.SliderThumb(color = hsv.toColor())
}

private val DefaultTrack = @Composable { hsv: Hsv ->
    ColorPickerDefaults.SaturationTrack(hsv = hsv)
}

@Composable
fun SaturationSlider(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb,
    track: @Composable (Hsv) -> Unit = DefaultTrack
) {
    SaturationSlider(
        hsv = colorState.hsv,
        onHsvChange = { colorState.hsv = it },
        modifier = modifier,
        thumb = thumb,
        track = track
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaturationSlider(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    modifier: Modifier = Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb,
    track: @Composable (Hsv) -> Unit = DefaultTrack
) {
    Slider(
        value = hsv.saturation,
        onValueChange = { onHsvChange(hsv.copy(saturation = it)) },
        modifier = modifier,
        valueRange = 0f .. 1f,
        thumb = { thumb(hsv) },
        track = { track(hsv) }
    )
}

@Preview(showBackground = true)
@Composable
private fun SaturationSliderPreview() {
    SaturationSlider(
        colorState = rememberColorState(
            initialColor = Hsv(hue = 0f, saturation = 0.5f, value = 1f).toColor()
        )
    )
}
