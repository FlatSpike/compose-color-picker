package com.github.flatspike.compose.color.picker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.flatspike.compose.color.picker.util.borderColor
import com.github.flatspike.compose.color.picker.util.drawCheckerboard

object ColorPickerDefaults {

    private val DefaultBorderWidth = 2.dp
    private val DefaultSliderThumbWidth = 16.dp
    private val DefaultSliderThumbHeight = 48.dp
    private val DefaultSliderTrackHeight = DefaultSliderThumbHeight
    private val DefaultSliderTrackShape = RoundedCornerShape(8.dp)
    private val DefaultPaletteThumbSize = 36.dp

    @Composable
    fun SliderThumb(
        color: Color,
        modifier: Modifier = Modifier,
        borderColor: Color = color.borderColor(),
    ) {
        Spacer(modifier = modifier
            .size(DefaultSliderThumbWidth, DefaultSliderThumbHeight)
            .clip(CircleShape)
            .background(color.copy(alpha = 1f))
            .border(BorderStroke(DefaultBorderWidth, borderColor), CircleShape)
        )
    }

    @Composable
    fun PaletteThumb(
        color: Color,
        modifier: Modifier = Modifier,
        borderColor: Color = color.borderColor()
    ) {
        val maxAlphaColor = color.copy(alpha = 1f)
        Spacer(modifier = modifier
            .size(DefaultPaletteThumbSize)
            .clip(CircleShape)
            .background(maxAlphaColor)
            .border(BorderStroke(DefaultBorderWidth, borderColor), CircleShape)
        )
    }

    @Composable
    fun AlphaTrack(
        color: Color,
        modifier: Modifier = Modifier
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(DefaultSliderTrackHeight)
            .clip(DefaultSliderTrackShape)
            .drawBehind {
                drawCheckerboard()
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            color.copy(alpha = 1f)
                        )
                    )
                )
            }
        )
    }

    @Composable
    fun RedTrack(
        color: Color,
        modifier: Modifier = Modifier
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(DefaultSliderTrackHeight)
            .clip(DefaultSliderTrackShape)
            .drawBehind {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            color.copy(red = 0f, alpha = 1f),
                            color.copy(red = 1f, alpha = 1f)
                        )
                    )
                )
            }
        )
    }

    @Composable
    fun BlueTrack(
        color: Color,
        modifier: Modifier = Modifier
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(DefaultSliderTrackHeight)
            .clip(DefaultSliderTrackShape)
            .drawBehind {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            color.copy(blue = 0f, alpha = 1f),
                            color.copy(blue = 1f, alpha = 1f)
                        )
                    )
                )
            }
        )
    }

    @Composable
    fun GreenTrack(
        color: Color,
        modifier: Modifier = Modifier
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(DefaultSliderTrackHeight)
            .clip(DefaultSliderTrackShape)
            .drawBehind {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            color.copy(green = 0f, alpha = 1f),
                            color.copy(green = 1f, alpha = 1f)
                        )
                    )
                )
            }
        )
    }

    @Composable
    fun ValueTrack(
        hsv: Hsv,
        modifier: Modifier = Modifier
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(DefaultSliderTrackHeight)
            .clip(DefaultSliderTrackShape)
            .drawBehind {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Black,
                            hsv.copy(value = 1f).toColor()
                        )
                    )
                )
            }
        )
    }

    @Composable
    fun SaturationTrack(
        hsv: Hsv,
        modifier: Modifier = Modifier
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(DefaultSliderTrackHeight)
            .clip(DefaultSliderTrackShape)
            .drawBehind {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            hsv.copy(saturation = 0f).toColor(),
                            hsv.copy(saturation = 1f).toColor()
                        )
                    )
                )
            }
        )
    }

    @Composable
    fun HueTrack(modifier: Modifier = Modifier) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(DefaultSliderTrackHeight)
            .clip(DefaultSliderTrackShape)
            .drawBehind {
                drawRect(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green,
                            Color.Cyan,
                            Color.Blue,
                            Color.Magenta,
                            Color.Red
                        )
                    )
                )
            }
        )
    }
}