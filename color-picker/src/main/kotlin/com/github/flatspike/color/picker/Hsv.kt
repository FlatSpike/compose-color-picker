package com.github.flatspike.color.picker

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.colorspace.Rgb


data class Hsv(
    val hue: Float = 0f,
    val saturation: Float = 0f,
    val value: Float = 1f
) {

    init {
        require(hue in 0f..360f) { "Hue $hue must be in range [0..360]" }
        require(saturation in 0f..1f) { "Saturation $saturation must be in range [0..1]" }
        require(value in 0f..1f) { "Value $value must be in range [0..1]" }
    }

    companion object {

        @Suppress("unused")
        val White = Hsv(0f, 0f, 1f)

        @Suppress("unused")
        val Black = Hsv(0f, 0f, 0f)

        @Suppress("unused")
        val Saver = listSaver(
            save = { listOf(it.hue, it.saturation, it.value) },
            restore = { Hsv(it[0] as Float, it[1] as Float, it[2] as Float) }
        )
    }
}

fun Hsv(color: Color): Hsv {
    require(color.colorSpace is Rgb) {
        "Can`t create Hsv from color with non Rgb colorSpace ${color.colorSpace}"
    }
    val (red, green, blue) = color
    val value = maxOf(red, green, blue)
    val delta = value - minOf(red, green, blue)
    val hue = when {
        delta == 0f -> 0f
        value == red -> 60f * (((green - blue) / delta) % 6)
        value == green -> 60f * (((blue - red) / delta) + 2)
        value == blue -> 60f * (((red - green) / delta) + 4)
        else -> throw IllegalArgumentException(
            "Fail to calculate hue value from $($red, $green, $blue)"
        )
    }.let { (360 + it) % 360 }
    val saturation = if (value == 0f) 0f else delta / value
    return Hsv(hue, saturation, value)
}

val Color.hsv get() = Hsv(this)

fun Hsv.toColor(
    alpha: Float = 1f,
    colorSpace: Rgb = ColorSpaces.Srgb
): Color = Color.hsv(hue, saturation, value, alpha, colorSpace)