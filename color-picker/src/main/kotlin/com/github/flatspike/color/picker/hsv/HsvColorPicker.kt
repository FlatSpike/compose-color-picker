package com.github.flatspike.color.picker.hsv

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.flatspike.color.picker.AlphaSlider

@Composable
fun HsvColorPicker(
    state: HsvColorState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        HsvColorPalette(
            modifier = Modifier.padding(16.dp),
            state = state
        )

        HsvValueSlider(
            modifier = Modifier.padding(8.dp),
            state = state
        )

        AlphaSlider(
            modifier = Modifier.padding(8.dp),
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
