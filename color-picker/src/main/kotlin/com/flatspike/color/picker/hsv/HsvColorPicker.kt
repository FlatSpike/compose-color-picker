package com.flatspike.color.picker.hsv

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.flatspike.color.picker.AlphaSlider

@Composable
fun HsvColorPicker(
    state: HsvColorState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HsvColorPalette(state = state)

        HsvValueSlider(state = state)

        AlphaSlider(
            color = state.color,
            onColorChange = { state.color = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HsvColorPickerPreview() {
    HsvColorPicker(state = rememberHsvColorState(initialColor = Color.White))
}
