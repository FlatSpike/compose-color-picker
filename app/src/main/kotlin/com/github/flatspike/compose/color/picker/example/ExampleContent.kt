package com.github.flatspike.compose.color.picker.example

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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.flatspike.compose.color.picker.AlphaSlider
import com.github.flatspike.compose.color.picker.BlueSlider
import com.github.flatspike.compose.color.picker.CirclePalette
import com.github.flatspike.compose.color.picker.ColorTextMappers
import com.github.flatspike.compose.color.picker.ColorTile
import com.github.flatspike.compose.color.picker.GreenSlider
import com.github.flatspike.compose.color.picker.HsvTextMappers
import com.github.flatspike.compose.color.picker.HueSlider
import com.github.flatspike.compose.color.picker.RedSlider
import com.github.flatspike.compose.color.picker.RingPalette
import com.github.flatspike.compose.color.picker.SaturationSlider
import com.github.flatspike.compose.color.picker.ValueSlider
import com.github.flatspike.compose.color.picker.rememberColorState
import com.github.flatspike.compose.color.picker.rememberColorTextState
import com.github.flatspike.compose.color.picker.rememberHsvTextState

enum class SelectedPalette {
    Circle,
    Ring
}

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> T): T =
    if (condition) block() else this

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
            val colorTextState = rememberColorTextState(
                colorState = colorState,
                mapper = ColorTextMappers.Hex
            )
            OutlinedTextField(
                value = colorTextState.text,
                onValueChange = { colorTextState.text = it },
                modifier = Modifier.weight(1f),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Done
                ),
                label = { Text(text = "Color") },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_content_copy),
                        contentDescription = "Copy",
                        modifier = Modifier.clickable {
                            clipboardManager.setText(AnnotatedString(colorTextState.text))
                        }
                    )
                }
            )
        }

        SliderRow(
            slider = { HueSlider(colorState = colorState) },
            textField = {
                val hsvTextState = rememberHsvTextState(
                    colorState = colorState,
                    mapper = HsvTextMappers.Hue
                )
                OutlinedTextField(
                    value = hsvTextState.text,
                    onValueChange = { hsvTextState.text = it },
                    label = { Text(text = "Hue") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        )

        SliderRow(
            slider = { SaturationSlider(colorState = colorState) },
            textField = {
                val hsvTextState = rememberHsvTextState(
                    colorState = colorState,
                    mapper = HsvTextMappers.Saturation
                )
                OutlinedTextField(
                    value = hsvTextState.text,
                    onValueChange = { hsvTextState.text = it },
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
                val hsvTextState = rememberHsvTextState(
                    colorState = colorState,
                    mapper = HsvTextMappers.Value
                )
                OutlinedTextField(
                    value = hsvTextState.text,
                    onValueChange = { hsvTextState.text = it },
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
                val colorTextState = rememberColorTextState(
                    colorState = colorState,
                    mapper = ColorTextMappers.Alpha
                )
                OutlinedTextField(
                    value = colorTextState.text,
                    onValueChange = { colorTextState.text = it },
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
                val colorTextState = rememberColorTextState(
                    colorState = colorState,
                    mapper = ColorTextMappers.Red
                )
                OutlinedTextField(
                    value = colorTextState.text,
                    onValueChange = { colorTextState.text = it },
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
                val colorTextState = rememberColorTextState(
                    colorState = colorState,
                    mapper = ColorTextMappers.Green
                )
                OutlinedTextField(
                    modifier = Modifier.width(76.dp),
                    value = colorTextState.text,
                    onValueChange = { colorTextState.text = it },
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
                val colorTextState = rememberColorTextState(
                    colorState = colorState,
                    mapper = ColorTextMappers.Blue
                )
                OutlinedTextField(
                    value = colorTextState.text,
                    onValueChange = { colorTextState.text = it },
                    label = { Text(text = "Blue") },
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