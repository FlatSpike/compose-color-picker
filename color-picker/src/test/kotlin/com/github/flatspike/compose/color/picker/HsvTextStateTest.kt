package com.github.flatspike.compose.color.picker

import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class HsvTextStateTest {

    @Test
    fun `hsv will change on setting valid text`() {
        val state = HsvTextState(Hsv(0f, 1f, 1f), StubHsvTextMapper)
        assertEquals(Hsv(0f, 1f, 1f), state.hsv)
        state.text = "green"
        assertEquals(Hsv(120f, 1f, 1f), state.hsv)
        state.text = "blue"
        assertEquals(Hsv(240f, 1f, 1f), state.hsv)
        state.text = "red"
        assertEquals(Hsv(0f, 1f, 1f), state.hsv)
    }

    @Test
    fun `hsv will not change on setting invalid text`() {
        val state = HsvTextState(Hsv(0f, 1f, 1f), StubHsvTextMapper)
        assertEquals(Hsv(0f, 1f, 1f), state.hsv)
        state.text = ""
        assertEquals(Hsv(0f, 1f, 1f), state.hsv)
        state.text = "yellow"
        assertEquals(Hsv(0f, 1f, 1f), state.hsv)
    }

    @Test
    fun `text will change on setting different hsv`() {
        val state = HsvTextState(Hsv(0f, 1f, 1f), StubHsvTextMapper)
        assertEquals("red", state.text)
        state.hsv = Hsv(120f, 1f, 1f)
        assertEquals("green", state.text)
        state.hsv = Hsv(240f, 1f, 1f)
        assertEquals("blue", state.text)
    }

    @Test
    fun `text will not change on setting same hsv`() {
        val state = HsvTextState(Hsv(0f, 1f, 1f), StubHsvTextMapper)
        assertEquals("red", state.text)
        state.hsv = Hsv(0f, 1f, 1f)
        assertEquals("red", state.text)
    }

    private object StubHsvTextMapper : HsvTextMapper {

        private val textHsv = listOf(
            "red" to Hsv(0f, 1f, 1f),
            "green" to Hsv(120f, 1f, 1f),
            "blue" to Hsv(240f, 1f, 1f)
        )

        override fun isValid(text: String): Boolean = textHsv.any { it.first == text }

        override fun toHsv(text: String, currentHsv: Hsv): Hsv =
            textHsv.first { it.first == text }.second

        override fun toText(hsv: Hsv, currentText: String): String =
            textHsv.first { it.second == hsv }.first
    }
}