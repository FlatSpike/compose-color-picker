package com.flatspike.color.picker.example

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flatspike.color.picker.AlphaSlider
import com.flatspike.color.picker.BlueSlider
import com.flatspike.color.picker.GreenSlider
import com.flatspike.color.picker.RedSlider
import com.flatspike.color.picker.hsv.HsvColorPalette
import com.flatspike.color.picker.hsv.HsvHueSlider
import com.flatspike.color.picker.hsv.HsvSaturationSlider
import com.flatspike.color.picker.hsv.HsvValueSlider
import com.flatspike.color.picker.hsv.rememberHsvColorState

@Composable
fun ExampleContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        val hsvColorState = rememberHsvColorState(initialColor = Color.White)

        HsvColorPalette(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            state = hsvColorState
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            HsvHueSlider(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 8.dp, end = 16.dp),
                state = hsvColorState
            )

            OutlinedTextField(
                modifier = Modifier.width(76.dp),
                value = hsvColorState.hsv.hue.toString(),
                onValueChange = { string ->
                    string
                        .toFloatOrNull()
                        ?.let { if (it in 0f..360f) it else null }
                        ?.also { hsvColorState.hsv = hsvColorState.hsv.copy(hue = it) }
                },
                label = { Text(text = "Hue") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            HsvSaturationSlider(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 8.dp, end = 16.dp),
                state = hsvColorState
            )

            OutlinedTextField(
                modifier = Modifier.width(76.dp),
                value = hsvColorState.hsv.saturation.toString(),
                onValueChange = { string ->
                    string
                        .toFloatOrNull()
                        ?.let { if (it in 0f..1f) it else null }
                        ?.also { hsvColorState.hsv = hsvColorState.hsv.copy(saturation = it) }
                },
                label = {
                    Text(
                        text = "Saturation",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            HsvValueSlider(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 8.dp, end = 16.dp),
                state = hsvColorState
            )

            OutlinedTextField(
                modifier = Modifier.width(76.dp),
                value = hsvColorState.hsv.value.toString(),
                onValueChange = { string ->
                    string
                        .toFloatOrNull()
                        ?.let { if (it in 0f..1f) it else null }
                        ?.also { hsvColorState.hsv = hsvColorState.hsv.copy(value = it) }
                },
                label = { Text(text = "Value") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            AlphaSlider(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 8.dp, end = 16.dp),
                color = hsvColorState.color,
                onColorChange = { hsvColorState.color = it }
            )

            OutlinedTextField(
                modifier = Modifier.width(76.dp),
                value = hsvColorState.color.alpha.toString(),
                onValueChange = { string ->
                    string
                        .toFloatOrNull()
                        ?.let { if (it in 0f..1f) it else null }
                        ?.also { hsvColorState.color = hsvColorState.color.copy(alpha = it) }
                },
                label = { Text(text = "Alpha") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            RedSlider(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 8.dp, end = 16.dp),
                color = hsvColorState.color,
                onColorChange = { hsvColorState.color = it }
            )

            OutlinedTextField(
                modifier = Modifier.width(76.dp),
                value = hsvColorState.color.red.toString(),
                onValueChange = { string ->
                    string
                        .toFloatOrNull()
                        ?.let { if (it in 0f..1f) it else null }
                        ?.also { hsvColorState.color = hsvColorState.color.copy(red = it) }
                },
                label = { Text(text = "Red") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            GreenSlider(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 8.dp, end = 16.dp),
                color = hsvColorState.color,
                onColorChange = { hsvColorState.color = it }
            )

            OutlinedTextField(
                modifier = Modifier.width(76.dp),
                value = hsvColorState.color.green.toString(),
                onValueChange = { string ->
                    string
                        .toFloatOrNull()
                        ?.let { if (it in 0f..1f) it else null }
                        ?.also { hsvColorState.color = hsvColorState.color.copy(green = it) }
                },
                label = { Text(text = "Green") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 8.dp)
        ) {
            BlueSlider(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 8.dp, start = 8.dp, end = 16.dp),
                color = hsvColorState.color,
                onColorChange = { hsvColorState.color = it }
            )

            OutlinedTextField(
                modifier = Modifier.width(76.dp),
                value = hsvColorState.color.blue.toString(),
                onValueChange = { string ->
                    string
                        .toFloatOrNull()
                        ?.let { if (it in 0f..1f) it else null }
                        ?.also { hsvColorState.color = hsvColorState.color.copy(blue = it) }
                },
                label = { Text(text = "Green") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExampleContentPreview() {
    ExampleTheme {
        ExampleContent()
    }
}