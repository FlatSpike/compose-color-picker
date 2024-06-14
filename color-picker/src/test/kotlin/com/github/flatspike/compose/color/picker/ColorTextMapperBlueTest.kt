package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.ColorTextMappers.Blue
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert.*
import org.junit.Test

class ColorTextMapperBlueTest {

    @Test
    fun `isValid return true`() {
        assert(Blue.isValid(""))
        assert(Blue.isValid("0"))
        assert(Blue.isValid("0."))
        assert(Blue.isValid("0.0"))
        assert(Blue.isValid("9"))
        assert(Blue.isValid("9."))
        assert(Blue.isValid("9.0"))
        assert(Blue.isValid("99.9"))
        assert(Blue.isValid("100.0"))
    }

    @Test
    fun `isValid return false`() {
        assertFalse(Blue.isValid(" "))
        assertFalse(Blue.isValid("."))
        assertFalse(Blue.isValid("-1"))
        assertFalse(Blue.isValid("1.00"))
        assertFalse(Blue.isValid("100.1"))
        assertFalse(Blue.isValid("text"))
    }

    @Test
    fun `toColor return valid color`() {
        assertEquals(Color(0f, 0f, 0f, 0f), Blue.toColor("", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Blue.toColor("0", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Blue.toColor("0.", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Blue.toColor("0.0", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0.09f, 0f), Blue.toColor("9", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0.09f, 0f), Blue.toColor("9.", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0.09f, 0f), Blue.toColor("9.0", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0.999f, 0f), Blue.toColor("99.9", Color.Unspecified))
        assertEquals(Color(0f, 0f, 1f, 0f), Blue.toColor("100.0", Color.Unspecified))
    }

    @Test
    fun `toText return valid text`() {
        assertEquals("0", Blue.toText(Color(0f, 0f, 0f, 0f), ""))
        assertEquals("0.4", Blue.toText(Color(0f, 0f, 0.002f, 0f), ""))
        assertEquals("0.8", Blue.toText(Color(0f, 0f, 0.006f, 0f), ""))
        assertEquals("1.2", Blue.toText(Color(0f, 0f, 0.010f, 0f), ""))
        assertEquals("1.6", Blue.toText(Color(0f, 0f, 0.014f, 0f), ""))
        assertEquals("100", Blue.toText(Color(0f, 0f, 0.999f, 0f), ""))
        assertEquals("100", Blue.toText(Color(0f, 0f, 1f, 0f), ""))
    }
}