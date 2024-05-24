package com.github.flatspike.color.picker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

private val DefaultThumb = @Composable { hsv: Hsv ->
    ColorPickerDefaults.SliderThumb(color = hsv.toColor())
}

private val DefaultTrack = @Composable { hsv: Hsv ->
    ColorPickerDefaults.ValueTrack(hsv = hsv)
}

@Composable
fun ValueSlider(
    colorState: ColorState,
    modifier: Modifier = Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb,
    track: @Composable (Hsv) -> Unit = DefaultTrack
) {
    ValueSlider(
        hsv = colorState.hsv,
        onHsvChange = { colorState.hsv = it },
        modifier = modifier,
        thumb = thumb,
        track = track
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValueSlider(
    hsv: Hsv,
    onHsvChange: (Hsv) -> Unit,
    modifier: Modifier = Modifier,
    thumb: @Composable (Hsv) -> Unit = DefaultThumb,
    track: @Composable (Hsv) -> Unit = DefaultTrack
) {
    Slider(
        value = hsv.value,
        onValueChange = { onHsvChange(hsv.copy(value = it)) },
        modifier = modifier,
        valueRange = 0f .. 1f,
        thumb = { thumb(hsv) },
        track = { track(hsv) }
    )
}

@Preview(showBackground = true)
@Composable
private fun ValueSliderPreview() {
    ValueSlider(
        colorState = rememberColorState(
            initialColor = Hsv(hue = 0f, saturation = 1f, value = 0.5f).toColor()
        )
    )
}
