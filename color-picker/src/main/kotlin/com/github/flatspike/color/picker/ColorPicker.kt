package com.github.flatspike.color.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
