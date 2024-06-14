package com.github.flatspike.compose.color.picker

import androidx.compose.ui.graphics.Color
import com.github.flatspike.compose.color.picker.ColorTextMappers.Green
import com.github.flatspike.compose.color.picker.util.assertEquals
import org.junit.Assert.*
import org.junit.Test

class ColorTextMapperGreenTest {

    @Test
    fun `isValid return true`() {
        assert(Green.isValid(""))
        assert(Green.isValid("0"))
        assert(Green.isValid("0."))
        assert(Green.isValid("0.0"))
        assert(Green.isValid("9"))
        assert(Green.isValid("9."))
        assert(Green.isValid("9.0"))
        assert(Green.isValid("99.9"))
        assert(Green.isValid("100.0"))
    }

    @Test
    fun `isValid return false`() {
        assertFalse(Green.isValid(" "))
        assertFalse(Green.isValid("."))
        assertFalse(Green.isValid("-1"))
        assertFalse(Green.isValid("1.00"))
        assertFalse(Green.isValid("100.1"))
        assertFalse(Green.isValid("text"))
    }

    @Test
    fun `toColor return valid color`() {
        assertEquals(Color(0f, 0f, 0f, 0f), Green.toColor("", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Green.toColor("0", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Green.toColor("0.", Color.Unspecified))
        assertEquals(Color(0f, 0f, 0f, 0f), Green.toColor("0.0", Color.Unspecified))
        assertEquals(Color(0f, 0.09f, 0f, 0f), Green.toColor("9", Color.Unspecified))
        assertEquals(Color(0f, 0.09f, 0f, 0f), Green.toColor("9.", Color.Unspecified))
        assertEquals(Color(0f, 0.09f, 0f, 0f), Green.toColor("9.0", Color.Unspecified))
        assertEquals(Color(0f, 0.999f, 0f, 0f), Green.toColor("99.9", Color.Unspecified))
        assertEquals(Color(0f, 1f, 0f, 0f), Green.toColor("100.0", Color.Unspecified))
    }

    @Test
    fun `toText return valid text`() {
        assertEquals("0", Green.toText(Color(0f, 0f, 0f, 0f), ""))
        assertEquals("0.4", Green.toText(Color(0f, 0.002f, 0f, 0f), ""))
        assertEquals("0.8", Green.toText(Color(0f, 0.006f, 0f, 0f), ""))
        assertEquals("1.2", Green.toText(Color(0f, 0.010f, 0f, 0f), ""))
        assertEquals("1.6", Green.toText(Color(0f, 0.014f, 0f, 0f), ""))
        assertEquals("100", Green.toText(Color(0f, 0.999f, 0f, 0f), ""))
        assertEquals("100", Green.toText(Color(0f, 1f, 0f, 0f), ""))
    }
}