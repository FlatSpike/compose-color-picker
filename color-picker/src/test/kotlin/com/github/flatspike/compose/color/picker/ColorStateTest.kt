package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Test

class ColorStateTest {

    @Test
    fun `color will change on setting different hsv`() {
        val colorState = ColorState(Color.Red)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
        colorState.color = Color(0.5f, 0f, 0f, 1f)
        assertEquals(Hsv(0f, 1f, 0.5f), colorState.hsv)
        colorState.color = Color(0.5f, 0f, 0f, 0.5f)
        assertEquals(Hsv(0f, 1f, 0.5f), colorState.hsv)
    }

    @Test
    fun `color will not change on setting the same hsv`() {
        val colorState = ColorState(Color.Red)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
        colorState.hsv = Hsv(0f, 1f, 1f)
        assertEquals(Color.Red, colorState.color)
        colorState.hsv = Hsv(0f, 1f, 1f)
        assertEquals(Color(1f, 0f, 0f, 1f), colorState.color)
    }

    @Test
    fun `hsv will change on setting different color`() {
        val colorState = ColorState(Color.Red)
        assertEquals(Color(1f, 0f, 0f, 1f), colorState.color)
        colorState.hsv = Hsv(0f, 1f, 0.5f)
        assertEquals(Color(0.5f, 0f, 0f, 1f), colorState.color)
        colorState.hsv = Hsv(0f, 0.5f, 0.5f)
        assertEquals(Color(0.5f, 0.25f, 0.25f, 1f), colorState.color)
        colorState.hsv = Hsv(0f, 0f, 0f)
        assertEquals(Color(0f, 0f, 0f, 1f), colorState.color)
        colorState.hsv = Hsv(0f, 1f, 0f)
        assertEquals(Color(0f, 0f, 0f, 1f), colorState.color)
    }

    @Test
    fun `hsv will not change on setting the same color`() {
        val colorState = ColorState(Color.Red)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
        colorState.color = Color.Red
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
        colorState.color = Color(1f, 0f, 0f, 1f)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
        colorState.color = Color(1f, 0f, 0f, 0f)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
    }

    @Test
    fun `hsv will not change on setting the same color with different alpha`() {
        val colorState = ColorState(Color.Red)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
        colorState.color = colorState.color.copy(alpha = 0.5f)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
        colorState.color = colorState.color.copy(alpha = 0f)
        assertEquals(Hsv(0f, 1f, 1f), colorState.hsv)
    }
}
