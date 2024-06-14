package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.ColorTextMappers.Red
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert.*
import org.junit.Test

class ColorTextMapperRedTest {

    @Test
    fun `isValid return true`() {
        assert(Red.isValid(""))
        assert(Red.isValid("0"))
        assert(Red.isValid("0."))
        assert(Red.isValid("0.0"))
        assert(Red.isValid("9"))
        assert(Red.isValid("9."))
        assert(Red.isValid("9.0"))
        assert(Red.isValid("99.9"))
        assert(Red.isValid("100.0"))
    }

    @Test
    fun `isValid return false`() {
        assertFalse(Red.isValid(" "))
        assertFalse(Red.isValid("."))
        assertFalse(Red.isValid("-1"))
        assertFalse(Red.isValid("1.00"))
        assertFalse(Red.isValid("100.1"))
        assertFalse(Red.isValid("text"))
    }

    @Test
    fun `toColor return valid color`() {
        assertEquals(Color(0f, 0f, 0f, 0f), Red.toColor("", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Red.toColor("0", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Red.toColor("0.", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Red.toColor("0.0", Color.Unspecified))
        assertEquals(Color(0.09f, 0f, 0f, 0f), Red.toColor("9", Color.Unspecified))
        assertEquals(Color(0.09f, 0f, 0f, 0f), Red.toColor("9.", Color.Unspecified))
        assertEquals(Color(0.09f, 0f, 0f, 0f), Red.toColor("9.0", Color.Unspecified))
        assertEquals(Color(0.999f, 0f, 0f, 0f), Red.toColor("99.9", Color.Unspecified))
        assertEquals(Color(1f, 0f, 0f, 0f), Red.toColor("100.0", Color.Unspecified))
    }

    @Test
    fun `toText return valid text`() {
        assertEquals("0", Red.toText(Color(0f, 0f, 0f, 0f), ""))
        assertEquals("0.4", Red.toText(Color(0.002f, 0f, 0f, 0f), ""))
        assertEquals("0.8", Red.toText(Color(0.006f, 0f, 0f, 0f), ""))
        assertEquals("1.2", Red.toText(Color(0.010f, 0f, 0f, 0f), ""))
        assertEquals("1.6", Red.toText(Color(0.014f, 0f, 0f, 0f), ""))
        assertEquals("100", Red.toText(Color(0.999f, 0f, 0f, 0f), ""))
        assertEquals("100", Red.toText(Color(1f, 0f, 0f, 0f), ""))
    }
}