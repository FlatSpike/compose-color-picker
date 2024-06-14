package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert.*
import org.junit.Test

class ColorTextStateTest {

    @Test
    fun `color will change on setting valid text`() {
        val state = ColorTextState(Color(1f, 0f, 0f), StubColorTextMapper)
        assertEquals(Color(1f, 0f, 0f), state.color)
        state.text = "green"
        assertEquals(Color(0f, 1f, 0f), state.color)
        state.text = "blue"
        assertEquals(Color(0f, 0f, 1f), state.color)
        state.text = "red"
        assertEquals(Color(1f, 0f, 0f), state.color)
    }

    @Test
    fun `color will not change on setting invalid text`() {
        val state = ColorTextState(Color(1f, 0f, 0f), StubColorTextMapper)
        assertEquals(Color(1f, 0f, 0f), state.color)
        state.text = ""
        assertEquals(Color(1f, 0f, 0f), state.color)
        state.text = "yellow"
        assertEquals(Color(1f, 0f, 0f), state.color)
    }

    @Test
    fun `text will change on setting different color`() {
        val state = ColorTextState(Color(1f, 0f, 0f), StubColorTextMapper)
        assertEquals("red", state.text)
        state.color = Color.Green
        assertEquals("green", state.text)
        state.color = Color.Blue
        assertEquals("blue", state.text)
    }

    @Test
    fun `text will not change on setting same color`() {
        val state = ColorTextState(Color(1f, 0f, 0f), StubColorTextMapper)
        assertEquals("red", state.text)
        state.color = Color(1f, 0f, 0f)
        assertEquals("red", state.text)
    }

    private object StubColorTextMapper : ColorTextMapper {

        private val textColor = listOf(
            "red" to Color(1f, 0f, 0f),
            "green" to Color(0f, 1f, 0f),
            "blue" to Color(0f, 0f, 1f)
        )

        override fun isValid(text: String): Boolean = textColor.any { it.first == text }

        override fun toColor(text: String, currentColor: Color): Color =
            textColor.first { it.first == text }.second

        override fun toText(color: Color, currentText: String): String =
            textColor.first { it.second == color }.first
    }
}