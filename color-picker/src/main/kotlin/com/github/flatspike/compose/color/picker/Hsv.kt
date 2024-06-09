package com.github.flatspike.compose.color.picker

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.colorspace.Rgb

@Immutable
@JvmInline
value class Hsv(val raw: UInt) {

    @Stable
    val hue get() = ((raw shr 16) and 0xffffu).toFloat() / 65535f * 360f

    @Stable
    val saturation get() = ((raw shr 8) and 0xffu).toFloat() / 255f

    @Stable
    val value get() = (raw and 0xffu).toFloat() / 255f

    @Stable
    fun component1(): Float = hue

    @Stable
    fun component2(): Float = saturation

    @Stable
    fun component3(): Float = value

    @Stable
    fun copy(
        hue: Float = this.hue,
        saturation: Float = this.saturation,
        value: Float = this.value
    ) = Hsv(hue, saturation, value)

    override fun toString(): String = "Hsv($hue, $saturation, $value)"

    companion object {

        @Stable
        val White = Hsv(0f, 0f, 1f)

        @Stable
        val Black = Hsv(0f, 0f, 0f)
    }
}

@Stable
fun Hsv(raw: Int): Hsv = Hsv(raw.toUInt())

fun Hsv.toInt() = raw.toInt()

@Stable
fun Hsv(
    hue: Float = 0f,
    saturation: Float = 0f,
    value: Float = 1f
): Hsv {
    require(hue in 0f..360f) { "Hue $hue must be in range [0..360]" }
    require(saturation in 0f..1f) { "Saturation $saturation must be in range [0..1]" }
    require(value in 0f..1f) { "Value $value must be in range [0..1]" }
    return Hsv(
        (
            ((hue / 360f * 65535f + 0.5f).toInt() shl 16) or
            ((saturation * 255f + 0.5f).toInt() shl 8) or
            (value * 255f + 0.5f).toInt()
        ).toUInt()
    )
}

@Stable
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

@Stable
val Color.hsv get() = Hsv(this)

@Stable
fun Hsv.toColor(
    alpha: Float = 1f,
    colorSpace: Rgb = ColorSpaces.Srgb
): Color = Color.hsv(hue, saturation, value, alpha, colorSpace)