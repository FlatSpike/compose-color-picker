package com.github.flatspike.compose.color.picker.util

import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.Hsv
import org.junit.Assert.*

private const val DefaultDelta = 0.01f

private fun format(prefix: String, message: String): String =
    if (prefix.isBlank()) message else "$prefix $message"

internal fun assertEquals(message: String, expected: Color, actual: Color) {
    assertEquals(format(message, "Color.red"), expected.red, actual.red, DefaultDelta)
    assertEquals(format(message, "Color.green"), expected.green, actual.green, DefaultDelta)
    assertEquals(format(message, "Color.blue"), expected.blue, actual.blue, DefaultDelta)
    assertEquals(format(message, "Color.alpha"), expected.alpha, actual.alpha, DefaultDelta)
}

internal fun assertEquals(expected: Color, actual: Color) {
    assertEquals("", expected, actual)
}

internal fun assertEquals(message: String, expected: Hsv, actual: Hsv) {
    assertEquals(format(message, "Hsv.hue"), expected.hue, actual.hue, DefaultDelta)
    assertEquals(format(message, "Hsv.saturation"), expected.saturation, actual.saturation, DefaultDelta)
    assertEquals(format(message, "Hsv.value"), expected.value, actual.value, DefaultDelta)
}

internal fun assertEquals(expected: Hsv, actual: Hsv) {
    assertEquals("", expected, actual)
}