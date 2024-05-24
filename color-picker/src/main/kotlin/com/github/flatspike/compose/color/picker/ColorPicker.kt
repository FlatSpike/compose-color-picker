package com.github.flatspike.compose.color.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(
    colorState: ColorState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CirclePalette(colorState = colorState)
        ValueSlider(colorState = colorState)
        AlphaSlider(colorState = colorState)
    }
}

@Preview(showBackground = true)
@Composable
private fun ColorPickerPreview() {
    ColorPicker(colorState = rememberColorState(initialColor = Color.White))
}
