package com.github.flatspike.color.picker.example

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.flatspike.color.picker.AlphaSlider
import com.github.flatspike.color.picker.BlueSlider
import com.github.flatspike.color.picker.CirclePalette
import com.github.flatspike.color.picker.ColorTile
import com.github.flatspike.color.picker.GreenSlider
import com.github.flatspike.color.picker.HueSlider
import com.github.flatspike.color.picker.RedSlider
import com.github.flatspike.color.picker.RingPalette
import com.github.flatspike.color.picker.SaturationSlider
import com.github.flatspike.color.picker.ValueSlider
import com.github.flatspike.color.picker.rememberColorState

enum class SelectedPalette {
    Circle,
    Ring
}

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> T): T =
    if (condition) block() else this

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ExampleContent(
    selectedPalette: SelectedPalette = SelectedPalette.Circle
) {
    var selectedPaletteState by remember { mutableStateOf(selectedPalette) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val colorState = rememberColorState(initialColor = Color.White)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(
                onClick = { selectedPaletteState = SelectedPalette.Circle },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColors()
                    .applyIf(selectedPaletteState == SelectedPalette.Circle) {
                        copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    }
            ) {
                Text(text = "Circle")
            }
            TextButton(
                onClick = { selectedPaletteState = SelectedPalette.Ring },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.textButtonColors()
                    .applyIf(selectedPaletteState == SelectedPalette.Ring) {
                        copy(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    }
            ) {
                Text(text = "Ring")
            }
        }

        when (selectedPaletteState) {
            SelectedPalette.Circle -> {
                CirclePalette(
                    colorState = colorState,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
            SelectedPalette.Ring -> {
                RingPalette(
                    colorState = colorState,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorTile(
                color = colorState.color,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )

            val clipboardManager = LocalClipboardManager.current
            var textArgb by remember {
                val argb = colorState.color.toArgb()
                mutableStateOf(argb.toHexString() to argb)
            }
            OutlinedTextField(
                value = textArgb.let { (text, argb) ->
                    val stateArgb = colorState.color.toArgb()
                    if (argb == stateArgb) text else stateArgb.toHexString()
                },
                onValueChange = {
                    if (it.isBlank()) {
                        textArgb = "" to -1
                        colorState.color = Color(-1)
                    } else {
                        val argb = it.toIntOrNull(16)
                        if (argb != null) {
                            textArgb = it to argb
                            colorState.color = Color(argb)
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii
                ),
                label = { Text(text = "Color") },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_content_copy),
                        contentDescription = "Copy",
                        modifier = Modifier.clickable {
                            clipboardManager.setText(AnnotatedString(textArgb.first))
                        }
                    )
                }
            )
        }

        SliderRow(
            slider = { HueSlider(colorState = colorState) },
            textField = {
                OutlinedTextField(
                    value = colorState.hsv.hue.toString(),
                    onValueChange = { string ->
                        string
                            .toFloatOrNull()
                            ?.let { if (it in 0f..360f) it else null }
                            ?.also { colorState.hsv = colorState.hsv.copy(hue = it) }
                    },
                    label = { Text(text = "Hue") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )

        SliderRow(
            slider = { SaturationSlider(colorState = colorState) },
            textField = {
                OutlinedTextField(
                    value = colorState.hsv.saturation.toString(),
                    onValueChange = { string ->
                        string
                            .toFloatOrNull()
                            ?.let { if (it in 0f..1f) it else null }
                            ?.also { colorState.hsv = colorState.hsv.copy(saturation = it) }
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
        )

        SliderRow(
            slider = {
                ValueSlider(colorState = colorState)
            },
            textField = {
                OutlinedTextField(
                    value = colorState.hsv.value.toString(),
                    onValueChange = { string ->
                        string
                            .toFloatOrNull()
                            ?.let { if (it in 0f..1f) it else null }
                            ?.also { colorState.hsv = colorState.hsv.copy(value = it) }
                    },
                    label = { Text(text = "Value") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )

        SliderRow(
            slider = {
                AlphaSlider(colorState = colorState)
            },
            textField = {
                OutlinedTextField(
                    value = colorState.color.alpha.toString(),
                    onValueChange = { string ->
                        string
                            .toFloatOrNull()
                            ?.let { if (it in 0f..1f) it else null }
                            ?.also { colorState.color = colorState.color.copy(alpha = it) }
                    },
                    label = { Text(text = "Alpha") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )

        SliderRow(
            slider = {
                RedSlider(colorState = colorState)
            },
            textField = {
                OutlinedTextField(
                    value = colorState.color.red.toString(),
                    onValueChange = { string ->
                        string
                            .toFloatOrNull()
                            ?.let { if (it in 0f..1f) it else null }
                            ?.also { colorState.color = colorState.color.copy(red = it) }
                    },
                    label = { Text(text = "Red") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )

        SliderRow(
            slider = {
                GreenSlider(colorState = colorState)
            },
            textField = {
                OutlinedTextField(
                    modifier = Modifier.width(76.dp),
                    value = colorState.color.green.toString(),
                    onValueChange = { string ->
                        string
                            .toFloatOrNull()
                            ?.let { if (it in 0f..1f) it else null }
                            ?.also { colorState.color = colorState.color.copy(green = it) }
                    },
                    label = { Text(text = "Green") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )

        SliderRow(
            slider = {
                BlueSlider(colorState = colorState)
            },
            textField = {
                OutlinedTextField(
                    value = colorState.color.blue.toString(),
                    onValueChange = { string ->
                        string
                            .toFloatOrNull()
                            ?.let { if (it in 0f..1f) it else null }
                            ?.also { colorState.color = colorState.color.copy(blue = it) }
                    },
                    label = { Text(text = "Green") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )
    }
}

@Composable
private inline fun SliderRow(
    slider: @Composable () -> Unit,
    textField: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(4f)) {
            slider()
        }
        Box(modifier = Modifier.weight(1f)) {
            textField()
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